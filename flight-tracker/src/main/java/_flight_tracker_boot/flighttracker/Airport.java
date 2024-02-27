package _flight_tracker_boot.flighttracker;

public class Airport {
    protected String name;
    protected String code;
    protected City city;

    public Airport() {
    }

    public Airport(String name, String code, City city) {
        this.name = name;
        this.code = code;
        this.city = city;
    }
}
