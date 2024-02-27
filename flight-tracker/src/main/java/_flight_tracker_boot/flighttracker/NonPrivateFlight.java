package _flight_tracker_boot.flighttracker;

enum NonPrivateFlightType {
    CARGO, COMMERCIAL
}

public class NonPrivateFlight extends Flight {
    protected NonPrivateFlightType flightType;
    protected String airline;

    public NonPrivateFlight() {
    }

    public NonPrivateFlight(NonPrivateFlightType flightType, String airline) {
        this.flightType = flightType;
        this.airline = airline;
    }
}
