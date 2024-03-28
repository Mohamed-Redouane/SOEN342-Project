import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static boolean isAuthenticated = false;
    public static String accountType = "GUEST";

    private static void displayBasicFlightInfo(Flight returnedFlight) {
        System.out.println("\tFlight Number: " + returnedFlight.getFlightNumber());
        System.out.println("\tFlight Type: " + returnedFlight.getFlightType());
        System.out.println("\tFlight Source: " + returnedFlight.getSource());
        System.out.println("\tFlight Destination: " + returnedFlight.getDestination());
        System.out.println("\tScheduled Departure Time: " + returnedFlight.getScheduledDepartureTime());
        System.out.println("\tScheduled Arrival Time: " + returnedFlight.getScheduledArrivalTime());
    }

    static void runAirlineTask(Scanner kb, int action) {
        switch (action) {
            case 1:
                System.out.println("### --- Register a new Non-Private Flight --- ###");
                kb.nextLine();

                System.out.println("Enter your airline id: ");
                int airlineId = kb.nextInt();
                System.out.println("airline_id: " + airlineId);

                System.out.println("Enter Non Private FLIGHT data as below with no spaces and commas between fields:");
                System.out.println("(source,destination,departure time YYYY-MM-DD HH:MM:SS," +
                        "arrival time YYYY-MM-DD HH:MM:SS)");
                kb.nextLine();
                String flightInput = kb.nextLine();
                String[] flightData = flightInput.split(",");
                for (String data : flightData) {
                    System.out.print(data + ", ");
                }
                System.out.println();
                System.out.println("Creating new flight instance and storing...");
                Flight flight = new Flight();
                String flightResponse = flight.createFlight(flightData[0], flightData[1], flightData[2], flightData[3],
                        "NON_PRIVATE_FLIGHT", airlineId, accountType);
                System.out.println(flightResponse);
                break;
            case 2:
                System.out.println("### --- View Flights --- ###");
                kb.nextLine();

                System.out.println("Enter a source-destination pair separated by a comma (ex: YUL,YYZ)");
                String pairInput = kb.nextLine();
                String source = pairInput.split(",")[0];
                String destination = pairInput.split(",")[1];
                System.out.println("Searching flights from " + source + " to " + destination + " for " + accountType + "...");

                Flight flightSearch = new Flight();
                List<Flight> retrievedFlights = flightSearch.getFlights(source, destination, accountType, null);
                if (retrievedFlights != null) {
                    Airline al = new Airline();
                    Aircraft ac = new Aircraft();

                    System.out.println("### Flights from " + source + " to " + destination + ": ");
                    int counter = 0;
                    for (Flight returnedFlight : retrievedFlights) {
                        System.out.println("###################################################");
                        System.out.println("Flight " + (counter + 1) + ": ");
                        displayBasicFlightInfo(returnedFlight);
                        System.out.println("\tFlight Airline: " + al.getAirlineName(returnedFlight.getAirline_id()));
                        System.out.println("\tFlight Aircraft: " + ac.getAircraftName(returnedFlight.getAircraft_id()));
                        counter++;
                    }
                }
                break;
            default:
                break;
        }
    }

    static void runAirportTask(Scanner kb, int action) {
        switch (action) {
            case 1:
                System.out.println("### --- Register a new Private Flight --- ###");
                kb.nextLine();

                System.out.println("Enter Private FLIGHT data as below with no spaces and commas between fields:");
                System.out.println("(source,destination,departure time YYYY-MM-DD HH:MM:SS," +
                        "arrival time YYYY-MM-DD HH:MM:SS)");
                kb.nextLine();
                String flightInput = kb.nextLine();
                String[] flightData = flightInput.split(",");
                for (String data : flightData) {
                    System.out.print(data + ", ");
                }
                System.out.println();
                System.out.println("Creating new flight instance and storing...");
                Flight flight = new Flight();
                String flightResponse = flight.createFlight(flightData[0], flightData[1], flightData[2], flightData[3],
                        "PRIVATE_FLIGHT", 0, accountType);
                System.out.println(flightResponse);
                break;
            case 2:
                System.out.println("### --- View Flights --- ###");
                kb.nextLine();

                System.out.println("Enter your airport code: ");
                String airportCode = kb.nextLine();

                System.out.println("Enter a source-destination pair separated by a comma (ex: YUL,YYZ)");
                String pairInput = kb.nextLine();
                String source = pairInput.split(",")[0];
                String destination = pairInput.split(",")[1];
                System.out.println("Searching flights from " + source + " to " + destination + " for " + accountType + "...");

                Flight flightSearch = new Flight();
                List<Flight> retrievedFlights = flightSearch.getFlights(source, destination, accountType, airportCode);
                if (retrievedFlights != null) {
                    Airline al = new Airline();
                    Aircraft ac = new Aircraft();

                    System.out.println("### Flights from " + source + " to " + destination + ": ");
                    int counter = 0;
                    for (Flight returnedFlight : retrievedFlights) {
                        System.out.println("###################################################");
                        System.out.println("Flight " + (counter + 1) + ": ");
                        displayBasicFlightInfo(returnedFlight);
                        System.out.println("\tFlight Aircraft: " + ac.getAircraftName(returnedFlight.getAircraft_id()));

                        counter++;
                    }
                }
                break;
            default:
                break;
        }
    }

    static void runSystemAdminTask(Scanner kb, int action) {
        Airline airline = new Airline();
        Airport airport = new Airport();
        switch (action) {
            case 1:
                System.out.println("### --- Add an Airport to Airport Records --- ###");
                kb.nextLine();

                System.out.println("Enter the airport code: ");
                String newAirportCode = kb.nextLine();
                System.out.println("Enter the name of the airport: ");
                String name = kb.nextLine();
                System.out.println("Enter the city of the airport: ");
                String city = kb.nextLine();

                Airport ap = new Airport();
                ap.createAirport(newAirportCode, name, city);
                break;
            case 2:
                System.out.println("### --- View Flights --- ###");
                break;
            default:
                break;
        }
    }

    static void runPublicUserTask(Scanner kb, int action) {
        Flight flight = new Flight();
        switch (action) {
            case 1:
                System.out.println("### --- View Flights --- ###");
                System.out.println("Enter a source-destination pair separated by a comma (ex: YUL,YYZ)");
                kb.nextLine();
                String pairInput = kb.nextLine();
                String source = pairInput.split(",")[0];
                String destination = pairInput.split(",")[1];

                System.out.println("Searching flights from " + source + " to " + destination + " for " + accountType + "...");

                Flight flightSearch = new Flight();
                List<Flight> retrievedFlights = flightSearch.getFlights(source, destination, accountType, null);

                System.out.println("### Flights from " + source + " to " + destination + ": ");
                int counter = 0;
                for (Flight returnedFlight : retrievedFlights) {
                    System.out.println("###################################################");
                    System.out.println("Flight " + (counter + 1) + ": ");
                    displayBasicFlightInfo(returnedFlight);
                    counter++;
                }
                break;
            case 2:
                System.out.println("### --- Return to Main Menu --- ###");
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Flight-Tracker";
        String username = "root";
        String password = "Jb50055007";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement();
        } catch (Exception e) {
            System.out.println("An error occurred connecting to the SQL database.");
        }
        System.out.println("Successfully Connected to SQL Database!");

        System.out.println("### --> Welcome to the Flight Tracker <-- ###");

        System.out.println("Would you like to continue with an account? (y/n)");
        Scanner kb = new Scanner(System.in);
        String authResponse = kb.nextLine();

        boolean wantsToAuthenticate = authResponse.equalsIgnoreCase("y"); // If not, User is guest.
        int action = 0;
        if (wantsToAuthenticate) {
            System.out.println("Here are the different account types: ");
            System.out.println("\t1. Airline Admin");
            System.out.println("\t2. Airport Admin");
            System.out.println("\t3. System Admin");
            System.out.println("\t4. Continue as Public User");
            System.out.println("\nPlease enter the digit corresponding to your choice: ");
            int accountInput = kb.nextInt();

            switch (accountInput) {
                case 1:
                    accountType = "AIRLINE_ADMIN";
                    System.out.println("\n\nWelcome Airline Admin User!");
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1. Register a Non Private Flight");
                    System.out.println("\t2. View Flights");

                    System.out.println("\t--> Please enter the digit for your choice:");
                    action = kb.nextInt();
                    runAirlineTask(kb, action);
                    break;
                case 2:
                    accountType = "AIRPORT_ADMIN";
                    System.out.println("\n\nWelcome Airport Admin User!");
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1. Register a Private Flight");
                    System.out.println("\t2. View Flights");

                    System.out.println("\t\t--> Please enter the digit for your choice:");
                    action = kb.nextInt();
                    runAirportTask(kb, action);
                    break;
                case 3:
                    accountType = "SYSTEM_ADMIN";
                    System.out.println("\n\nWelcome System Admin User!");
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1. Add an Airport");

                    System.out.println("\t\t--> Please enter the digit for your choice:");
                    action = kb.nextInt();
                    runSystemAdminTask(kb, action);
                    break;
                default:
                    accountType = "GUEST";
                    System.out.println("\n\nWelcome to our Flight Tracker System!");

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("\t1. View Flights");
                    System.out.println("\t--> Please enter the digit for your choice:");
                    action = kb.nextInt();
                    runPublicUserTask(kb, action);
                    break;
            }
        }
        else { // Public User.
            accountType = "GUEST";
            System.out.println("\n\nWelcome to our Flight Tracker System!");

            System.out.println("\nWhat would you like to do?");
            System.out.println("\t1. View Flights");
            System.out.println("\t--> Please enter the digit for your choice:");
            action = kb.nextInt();
            runPublicUserTask(kb, action);
        }
    }
}