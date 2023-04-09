//Enum for the operations - Initialize/Insert/Delete/Search
public enum OperationCode {
    Insert("Insert"),
    UpdateTrip("UpdateTrip"),
    GetNextRide("GetNextRide"),
    CancelRide("CancelRide"),
    Print("Print");

    private final String label;
    OperationCode(String label){
        this.label = label;
    }

    public String toString(){
        return  this.label;
    }
}