package _flight_tracker_boot.flighttracker;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

enum FlightType {
    NON_PRIVATE_FLIGHT, PRIVATE_FLIGHT
}

@Document(collection = "flights")
@Data
public class Flight {
    @Id
    private ObjectId id;
    protected long flightNumber;
    protected String aircraft_name;
    protected String airport;
    protected String source;
    protected String destination;
    protected String scheduledDepartureTime;
    protected String actualDepartureTime;
    protected String scheduledArrivalTime;
    protected String estimatedArrivalTime;
    protected FlightType flightType;

    protected String airline_name;
    protected String airline_id;
    protected String aircraft_id;

    public Flight() {
    }

    public Flight(String aircraft_name, String airport, String source, String destination, long flightNumber,
            String scheduledDepartureTime, String actualDepartureTime, String scheduledArrivalTime,
            String estimatedArrivalTime, FlightType flightType, String airline_name, String airline_id,
            String aircraft_id) {
        this.aircraft_name = aircraft_name;
        this.airport = airport;
        this.source = source;
        this.destination = destination;
        this.flightNumber = flightNumber;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.actualDepartureTime = actualDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.flightType = flightType;
        this.airline_name = airline_name;
        this.airline_id = airline_id;
        this.aircraft_id = aircraft_id;
    }
}