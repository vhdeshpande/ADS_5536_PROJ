//Enum for the operations - Initialize/Insert/Delete/Search
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