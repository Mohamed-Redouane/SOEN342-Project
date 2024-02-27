package _flight_tracker_boot.flighttracker;

enum AircraftState {
    AIRPORT, IN_TRANSIT
}

public class Aircraft {
    protected String aircraftState;
    protected String model;
    protected long ID;
    protected long flightNumber; // From Flight Object

    public Aircraft() {
    }

    public Aircraft(String aircraftState, String model, long ID, long flightNumber) {
        this.aircraftState = aircraftState;
        this.model = model;
        this.ID = ID;
        this.flightNumber = flightNumber;
    }
}
