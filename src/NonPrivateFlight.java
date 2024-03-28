public class NonPrivateFlight extends Flight {
    public enum NonPrivateFlightType {
        CARGO, COMMERCIAL
    }
    protected String nonPrivateFlightType;

    public NonPrivateFlight() {}
    public NonPrivateFlight(String flightType) {
        this.nonPrivateFlightType = flightType;
    }

    public String getNonPrivateFlightType() {
        return this.nonPrivateFlightType;
    }
}