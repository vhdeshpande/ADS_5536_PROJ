/**
 * Enum for the operations to execute - Print, GetNextRide, Insert, CancelRide and UpdateTrip
 */
public enum GatorTaxiOperationCode {

    Print("Print"),

    GetNextRide("GetNextRide"),

    Insert("Insert"),

    CancelRide("CancelRide"),

    UpdateTrip("UpdateTrip");

    private final String operation;

    GatorTaxiOperationCode(String operation){
        this.operation = operation;
    }

    public String toString(){
        return  this.operation;
    }
}