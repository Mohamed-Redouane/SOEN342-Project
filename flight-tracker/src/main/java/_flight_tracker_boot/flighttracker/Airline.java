package _flight_tracker_boot.flighttracker;

import java.util.*;

public class Airline {
    protected String name;
    protected List<NonPrivateFlight> NonPrivateFlights;
    protected List<Aircraft> fleet;

    public Airline() {
    }

    public Airline(String name, List<NonPrivateFlight> NonPrivateFlights, List<Aircraft> fleet) {
        this.name = name;
        this.NonPrivateFlights = NonPrivateFlights;
        this.fleet = fleet;
    }
}
