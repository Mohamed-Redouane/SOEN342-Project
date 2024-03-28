import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

enum AircraftState {
    AIRPORT, IN_TRANSIT
}

public class Aircraft {
    protected int ID;
    protected String aircraftState;
    protected String model;
    String url = "jdbc:mysql://localhost:3306/Flight-Tracker";
    String username = "root";
    String password = "Jb50055007";

    public Aircraft() {}

    public Aircraft(int id, String aircraftState, String model) {
        this.ID = id;
        this.aircraftState = aircraftState;
        this.model = model;
    }
    public Aircraft (String aircraftState, String model) {
        this.aircraftState = aircraftState;
        this.model = model;
    }

    public int getId() {
        return this.ID;
    }
    public String getAircraftState() {
        return this.aircraftState;
    }
    public String getModel() {
        return this.model;
    }

    public String getAircraftName(int aircraftId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            String aircraftNameQuery = "SELECT Model FROM Aircrafts WHERE aircraft_id = ?";
            PreparedStatement aircraftStatement = conn.prepareStatement(aircraftNameQuery);
            aircraftStatement.setInt(1, aircraftId);
            ResultSet rs = aircraftStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            else {
                return "No aircraft for this id.";
            }
        }
        catch (Exception e) {
            return "Error finding aircraft.";
        }
    }
}
