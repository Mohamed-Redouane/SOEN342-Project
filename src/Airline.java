import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Airline {
    protected int airlineId;
    protected String name;
    String url = "jdbc:mysql://localhost:3306/Flight-Tracker";
    String username = "root";
    String password = "Jb50055007";

    public Airline() {}

    public Airline(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getAirlineName(int airlineId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            String airlineNameQuery = "SELECT Name FROM Airlines WHERE airline_id = ?";
            PreparedStatement airlineStatement = conn.prepareStatement(airlineNameQuery);
            airlineStatement.setInt(1, airlineId);
            ResultSet rs = airlineStatement.executeQuery();
            String name = "";
            if (rs.next()) {
                name = rs.getString(1);
                return name;
            }
            else {
                return "No airline for this airline ID.";
            }
        }
        catch (Exception e) {
            return "Error finding airline.";
        }
    }
}
