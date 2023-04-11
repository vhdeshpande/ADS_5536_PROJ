/**
 * Enum for the operations to execute - Print, GetNextRide, Insert, CancelRide and UpdateTrip
 */
public enum OperationCode {

    Print("Print"),

    GetNextRide("GetNextRide"),

    Insert("Insert"),

    CancelRide("CancelRide"),

    UpdateTrip("UpdateTrip");

    private final String operation;

    OperationCode(String operation){
        this.operation = operation;
    }

    public String toString(){
        return  this.operation;
    }
}