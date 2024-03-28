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
}
