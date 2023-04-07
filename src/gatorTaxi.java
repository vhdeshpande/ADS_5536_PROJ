import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.List;

public class gatorTaxi {

    public static final String NULL_STRING = "NULL";
    List<Operation> operations; //List to store operations read from file
    ResultWriter resultWriter; //Output file writer
    InputReader inputReader;//input file reader

    public gatorTaxi() {
        this.operations = new ArrayList<>();
        this.resultWriter = new ResultWriter();
        this.inputReader = new InputReader();
    }

    private void writeResult(String text) {
        try {
            this.resultWriter.writeToFile(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //wrapper to close the file
    private void closeWriter() {
        try {
            this.resultWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readOperations(String filename) throws Exception{
        this.operations = inputReader.getOperationsFromFile(filename);
    }

    private void performOperations() {
        for(Operation op: operations)
            this.writeResult(op.toString());
        closeWriter();
    }


    private void execute(String inputFile) throws Exception {
        this.readOperations(inputFile);
        this.performOperations();
    }

    public static void main(String[] args) {
        gatorTaxi gt = new gatorTaxi();

        String inputFilePath = args[0];
        try {
            gt.execute(inputFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}