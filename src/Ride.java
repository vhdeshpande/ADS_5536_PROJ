
public class Ride {
    private Integer rideNumber;
    private Integer rideCost;
    private Integer tripDuration;

    public Ride(Integer rideNumber, Integer rideCost, Integer tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    public Integer getRideNumber() {
        return rideNumber;
    }

    public void setRideNumber(Integer rideNumber) {
        this.rideNumber = rideNumber;
    }

    public Integer getRideCost() {
        return rideCost;
    }

    public void setRideCost(Integer rideCost) {
        this.rideCost = rideCost;
    }

    public Integer getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(Integer tripDuration) {
        this.tripDuration = tripDuration;
    }

    @Override
    public String toString() {
        return "(" + this.rideNumber + "," + this.rideCost + "," + this.tripDuration + ")";
    }
}
