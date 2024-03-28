import java.sql.*;
import java.util.*;

public class Flight {
    public enum FlightType {
        NON_PRIVATE_FLIGHT, PRIVATE_FLIGHT
    }

    protected int flightNumber;
    protected String source;
    protected String destination;
    protected int airline_id;
    protected int aircraft_id;
    protected String scheduledDepartureTime;
    protected String actualDepartureTime;
    protected String scheduledArrivalTime;
    protected String actualArrivalTime;
    protected String flightType;

    String url = "jdbc:mysql://localhost:3306/Flight-Tracker";
    String username = "root";
    String password = "Jb50055007";

    public Flight() {
    }
    // NON-PRIVATE FLIGHT Constructor

    // Different constructors for different ways of creating the object.
    public Flight(int flightNumber, String source, String destination, int airline_id,
            int aircraft_id, String scheduledDepartureTime, String scheduledArrivalTime, String flightType) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.airline_id = airline_id;
        this.aircraft_id = aircraft_id;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.flightType = flightType;
    }

    // For public users (non-registered clients)
    public Flight(int flightNumber, String source, String destination, String scheduledDepartureTime,
            String scheduledArrivalTime, String flightType) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.flightType = flightType;
    }

    // For registered Airline Administrators (Override)
    public Flight(String source, String destination, int airline_id,
            int aircraft_id, String scheduledDepartureTime, String scheduledArrivalTime, String flightType) {
        this.source = source;
        this.destination = destination;
        this.airline_id = airline_id;
        this.aircraft_id = aircraft_id;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.flightType = flightType;
    }

    // For registered Airport Administrators (Override)
    public Flight(int flightNumber, String source, String destination, int aircraft_id,
            String scheduledDepartureTime, String scheduledArrivalTime, String flightType) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.aircraft_id = aircraft_id;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.flightType = flightType;
    }

    public Flight(String source, String destination, int airline_id, int aircraft_id,
            String scheduledDepartureTime, String actualDepartureTime, String scheduledArrivalTime,
            String actualArrivalTime, String flightType) {
        this.source = source;
        this.destination = destination;
        this.airline_id = airline_id;
        this.aircraft_id = aircraft_id;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.actualDepartureTime = actualDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.actualArrivalTime = actualArrivalTime;
        this.flightType = flightType;
    }

    public int getFlightNumber() {
        return this.flightNumber;
    }

    public String getSource() {
        return this.source;
    }

    public String getDestination() {
        return this.destination;
    }

    public int getAirline_id() {
        return this.airline_id;
    }

    public int getAircraft_id() {
        return this.aircraft_id;
    }

    public String getScheduledDepartureTime() {
        return this.scheduledDepartureTime;
    }

    public String getScheduledArrivalTime() {
        return this.scheduledArrivalTime;
    }

    public String getFlightType() {
        return this.flightType;
    }

    private static String createFlightQuery(String accountType) {
        String createFlightQuery = "";
        if (accountType.equals("AIRLINE_ADMIN")) {
            createFlightQuery = "INSERT INTO Flights(source, destination, airline_id, aircraft_id, " +
                    "scheduledDepartureTime, scheduledArrivalTime, flightType) VALUES(?, ?, ?, ?, ?, ?, ?);";
        } else if (accountType.equals("AIRPORT_ADMIN")) {
            createFlightQuery = "INSERT INTO Flights(source, destination, aircraft_id, " +
                    "scheduledDepartureTime, scheduledArrivalTime, flightType) VALUES(?, ?, ?, ?, ?, ?);";
        }
        return createFlightQuery;
    }

    public String createFlight(String source, String destination, String scheduledDepartureTime,
            String scheduledArrivalTime, String flightType, int airlineId, String accountType) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);

            String departureConflictQuery = "SELECT COUNT(*) FROM Flights " +
                    "WHERE source = ? AND scheduledDepartureTime = ?;";
            PreparedStatement countStatement = conn.prepareStatement(departureConflictQuery);
            countStatement.setString(1, source);
            countStatement.setString(2, scheduledDepartureTime);
            System.out.println("Checking departure conflicts...");
            ResultSet rs = countStatement.executeQuery();
            int departureConflictCount = 0;
            if (rs.next()) {
                departureConflictCount = rs.getInt(1);
            }
            // No departure conflicts at this point.
            // Check arrival conflicts.
            int arrivalConflictCount = 0;
            if (departureConflictCount == 0) {
                System.out.println("No departure conflicts!\n\tChecking arrival conflicts...");
                String arrivalConflictQuery = "SELECT COUNT(*) FROM Flights " +
                        "WHERE destination = ? AND scheduledArrivalTime = ?;";
                countStatement = conn.prepareStatement(arrivalConflictQuery);
                countStatement.setString(1, destination);
                countStatement.setString(2, scheduledArrivalTime);
                rs = countStatement.executeQuery();
                if (rs.next()) {
                    arrivalConflictCount = rs.getInt(1);
                }
            }
            if (arrivalConflictCount == 0) {
                System.out.println("No arrival conflicts!");

                // Check if there is an Aircraft available for this flight.
                String checkAircraftQuery = "";
                PreparedStatement checkAircraftStatement = null;
                if (accountType.equals("AIRLINE_ADMIN")) {
                    checkAircraftQuery = "SELECT AF.aircraft_id, AF.flightNumber, F.airline_id, " +
                            "F.scheduledDepartureTime, F.scheduledArrivalTime " +
                            "FROM Flights AS F " +
                            "INNER JOIN AircraftFlights AS AF ON F.aircraft_id = AF.aircraft_id " +
                            "AND F.airline_id = ? " +
                            "WHERE (F.scheduledDepartureTime BETWEEN ? AND ?) " +
                            "OR (F.scheduledArrivalTime BETWEEN ? AND ?) " +
                            "OR (F.scheduledDepartureTime <= ? AND F.scheduledArrivalTime >= ?);";
                    checkAircraftStatement = conn.prepareStatement(checkAircraftQuery);
                    checkAircraftStatement.setInt(1, airlineId);
                    checkAircraftStatement.setString(2, scheduledDepartureTime);
                    checkAircraftStatement.setString(3, scheduledArrivalTime);
                    checkAircraftStatement.setString(4, scheduledDepartureTime);
                    checkAircraftStatement.setString(5, scheduledArrivalTime);
                    checkAircraftStatement.setString(6, scheduledDepartureTime);
                    checkAircraftStatement.setString(7, scheduledArrivalTime);
                } else if (accountType.equals("AIRPORT_ADMIN")) {
                    checkAircraftQuery = "SELECT AF.aircraft_id, AF.flightNumber, " +
                            "F.scheduledDepartureTime, F.scheduledArrivalTime " +
                            "FROM Flights AS F " +
                            "INNER JOIN AircraftFlights AS AF ON F.aircraft_id = AF.aircraft_id " +
                            "WHERE (F.scheduledDepartureTime BETWEEN ? AND ?) " +
                            "OR (F.scheduledArrivalTime BETWEEN ? AND ?) " +
                            "OR (F.scheduledDepartureTime <= ? AND F.scheduledArrivalTime >= ?);";
                    checkAircraftStatement = conn.prepareStatement(checkAircraftQuery);
                    checkAircraftStatement.setString(1, scheduledDepartureTime);
                    checkAircraftStatement.setString(2, scheduledArrivalTime);
                    checkAircraftStatement.setString(3, scheduledDepartureTime);
                    checkAircraftStatement.setString(4, scheduledArrivalTime);
                    checkAircraftStatement.setString(5, scheduledDepartureTime);
                    checkAircraftStatement.setString(6, scheduledArrivalTime);
                }
                assert checkAircraftStatement != null;
                ResultSet caasRs = checkAircraftStatement.executeQuery();
                ArrayList<Integer> afIDs = new ArrayList<>();
                int afID = 0;
                while (caasRs.next()) {
                    System.out.println("can't use aircraft " + caasRs.getInt("aircraft_id"));
                    afIDs.add(caasRs.getInt("aircraft_id"));
                }
                String getAircraftQuery = "";
                if (accountType.equals("AIRLINE_ADMIN"))
                    getAircraftQuery = "SELECT aircraft_id FROM Aircrafts WHERE airline_id = ?;";
                else
                    getAircraftQuery = "SELECT aircraft_id FROM Aircrafts;";

                PreparedStatement getAircraftStatement = conn.prepareStatement(getAircraftQuery);
                if (accountType.equals("AIRLINE_ADMIN"))
                    getAircraftStatement.setInt(1, airlineId);

                ResultSet aircrafts = getAircraftStatement.executeQuery();
                while (aircrafts.next()) {
                    if (!afIDs.contains(aircrafts.getInt("aircraft_id"))) {
                        afID = aircrafts.getInt("aircraft_id");
                        break;
                    }
                }
                if (afID == 0) {
                    System.out.println("No aircrafts are available for this flight.");
                    return null;
                }

                String createFlightQuery = createFlightQuery(accountType);
                PreparedStatement createStatement = conn.prepareStatement(createFlightQuery,
                        Statement.RETURN_GENERATED_KEYS);
                createStatement.setString(1, source);
                createStatement.setString(2, destination);
                if (accountType.equals("AIRLINE_ADMIN")) {
                    createStatement.setInt(3, airlineId);
                    createStatement.setInt(4, afID);
                    createStatement.setString(5, scheduledDepartureTime);
                    createStatement.setString(6, scheduledArrivalTime);
                    createStatement.setString(7, flightType);
                } else {
                    createStatement.setInt(3, afID);
                    createStatement.setString(4, scheduledDepartureTime);
                    createStatement.setString(5, scheduledArrivalTime);
                    createStatement.setString(6, flightType);
                }

                int rowsInserted = createStatement.executeUpdate();
                int generatedFlightNumber = 0;
                if (rowsInserted > 0) {
                    System.out.println("Successfully created flight.");
                    ResultSet generatedKeys = createStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedFlightNumber = generatedKeys.getInt(1); // Assuming flightNumber is an integer column
                        System.out.println("Generated flight number: " + generatedFlightNumber);
                        // You can use the generated flight number as needed
                    } else {
                        System.err.println("Failed to retrieve generated flight number.");
                    }
                }

                // Create Flight associations with Airline and Aircraft
                if (generatedFlightNumber != 0) {
                    System.out.println("Creating Aircraft-Flight association...");
                    String aircraftFlightQuery = "INSERT INTO AircraftFlights (aircraft_id, flightNumber) " +
                            "VALUES (?,?);";
                    PreparedStatement aircraftFlightStatement = conn.prepareStatement(aircraftFlightQuery);
                    aircraftFlightStatement.setInt(1, afID);
                    aircraftFlightStatement.setInt(2, generatedFlightNumber);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred storing the flight data.");
            return "Failed to store new flight.";
        }
        return "Flight stored.";
    }

    private static String getRegisteredFlightQuery(String accountType) {
        String getRegisteredFlightQuery = "";
        if (accountType.equals("AIRLINE_ADMIN")) {
            getRegisteredFlightQuery = "SELECT flightNumber, source, destination, airline_id, aircraft_id, " +
                    "scheduledDepartureTime, scheduledArrivalTime, flightType FROM Flights " +
                    "WHERE source = ? AND destination = ? AND flightType = ?;";
        }else if (accountType.equals("AIRPORT_ADMIN")) {
            getRegisteredFlightQuery = "SELECT flightNumber, source, destination, aircraft_id, " +
                    "scheduledDepartureTime, scheduledArrivalTime, flightType FROM Flights " +
                    "WHERE source = ? AND destination = ?;";
        }
        return getRegisteredFlightQuery;
    }
    public ArrayList<Flight> getFlights(String source, String destination, String accountType,
                                                                    String airportCode) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            if (accountType.equals("GUEST")) {
                String getGuestFlightQuery = "SELECT flightNumber, source, destination, scheduledDepartureTime, " +
                                             "scheduledArrivalTime, flightType FROM Flights " +
                                             "WHERE source = ? AND destination = ? AND flightType = ?;";
                PreparedStatement flightStatement = conn.prepareStatement(getGuestFlightQuery);
                flightStatement.setString(1, source);
                flightStatement.setString(2, destination);
                flightStatement.setString(3, "NON_PRIVATE_FLIGHT");
                System.out.println("Retrieving flight...");
                ResultSet rs = flightStatement.executeQuery();
                ArrayList<Flight> flights = new ArrayList<>();
                Flight flight = null;
                while (rs.next()) {
                    int flightNum = rs.getInt("flightNumber");
                    String flightSource = rs.getString("source");
                    String flightDest = rs.getString("destination");
                    String flightDeparture = rs.getString("scheduledDepartureTime");
                    String flightArrival = rs.getString("scheduledArrivalTime");
                    String flightType = rs.getString("flightType");
                    flight = new Flight(flightNum, flightSource, flightDest, flightDeparture, flightArrival, flightType);
                    flights.add(flight);
                }
                if (flight == null) {
                    System.out.println("No flights returned.");
                    return null;
                }
                
                System.out.println("Successfully retrieved flights.");
                return flights;
                
            }
            
    }
    catch (Exception e) {
        System.out.println("Something went wrong retrieving flights from the DB.");
        return null;
    }

}
