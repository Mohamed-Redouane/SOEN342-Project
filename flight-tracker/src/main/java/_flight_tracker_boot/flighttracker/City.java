package _flight_tracker_boot.flighttracker;

public class City {
    protected String name;
    protected String country;
    protected String temperature;

    public City() {
    }

    public City(String name, String country, String temperature) {
        this.name = name;
        this.country = country;
        this.temperature = temperature;
    }
}
