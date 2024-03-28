import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Airport {
    protected String name;
    protected String code;
    protected City city; // Association with class City, one-to-one relationship.
    String url = "jdbc:mysql://localhost:3306/Flight-Tracker";
    String username = "root";
    String password = "Jb50055007";

    public Airport() {}
    public Airport(String name, String code, City city) {
        this.name = name;
        this.code = code;
        this.city = city;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getCity() {
        return this.city.name;
    }

    public void createAirport(String airportCode, String airportName, String city) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);

            // Check if airport code already exists in Records.
            String checkCodeQuery = "SELECT COUNT(*) " +
                                    "FROM Airports " +
                                    "WHERE AirportCode = ?;";
            PreparedStatement checkCodeStatement = conn.prepareStatement(checkCodeQuery);
            checkCodeStatement.setString(1, airportCode);
            ResultSet rs = checkCodeStatement.executeQuery();
            int matchCount = 0;
            if (rs.next()) {
                matchCount = rs.getInt(1);
            }
            if (matchCount == 0) { // If true, the airport code is new and doesn't already exist.
                String insertAirportQuery = "INSERT INTO Airports (AirportCode, Name, City) " +
                                            "VALUES (?,?,?);";
                PreparedStatement createAirportStatement = conn.prepareStatement(insertAirportQuery);
                createAirportStatement.setString(1, airportCode);
                createAirportStatement.setString(2, airportName);
                createAirportStatement.setString(3, city);
                int insertRowsUpdated = createAirportStatement.executeUpdate();
                if (insertRowsUpdated > 0) {
                    System.out.println("Airport successfully stored in Records.");
                }
                else
                    System.out.println("Couldn't store airport in Records. Check if the city is stored.");
            }
            else {
                System.out.println("The airport already exists");
            }
        }
        catch (Exception e) {
            System.err.println("Failed to create the airport record.");
        }
    }
}