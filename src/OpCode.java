//Enum for the operations - Initialize/Insert/Delete/Search
public enum OpCode {
    Initialize("Initialize"),
    Insert("Insert"),
    UpdateTrip("UpdateTrip"),
    GetNextRide("GetNextRide"),
    CancelRide("CancelRide"),
    Print("Print");

    private final String label;
    OpCode(String label){
        this.label = label;
    }

    public String toString(){
        return  this.label;
    }
}