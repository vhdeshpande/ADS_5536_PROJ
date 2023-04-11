
public class GatorTaxiRide {
    private int rideNumber;
    private int rideCost;
    private int tripDuration;

    public GatorTaxiRide(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    public int getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(int rideNumber) {
        this.rideNumber = rideNumber;
    }

    public int getRideCost() {
        return rideCost;
    }

    public void setRideCost(int rideCost) {
        this.rideCost = rideCost;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    @Override
    public String toString() {
        return "(" + this.rideNumber + "," + this.rideCost + "," + this.tripDuration + ")";
    }
}
