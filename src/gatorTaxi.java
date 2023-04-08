import java.util.ArrayList;
import java.util.List;

public class gatorTaxi {

    public static final String NULL_STRING = "NULL";

    RedBlackTree redBlackTree = new RedBlackTree();;
    List<Operation> operations; //List to store operations read from file
    ResultWriter resultWriter; //Output file writer
    InputFileReader inputFileReader;//input file reader

    public gatorTaxi() {
        this.operations = new ArrayList<>();
        this.resultWriter = new ResultWriter();
        this.inputFileReader = new InputFileReader();
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
        this.operations = inputFileReader.getOperationsFromFile(filename);
    }

    private void performOperations() {
        for(Operation op: operations)
            switch (op.getOpcode()) {
                case Insert:
                    this.insert(op.getVal1(), op.getVal2(), op.getVal3());
                    break;
                case CancelRide:
                    this.cancelRide(op.getVal1());
                    break;
            }
//            this.writeResult(op.toString());
        closeWriter();
    }

    private void cancelRide(Integer rideNumber) {
        System.out.println("\n---Cancel---");
        this.redBlackTree.cancelRide(rideNumber);
        System.out.println();
        redBlackTree.inorderTraversal();
    }

    private void insert(int val1, int val2, int val3) {
        System.out.println("\n---Insert---");
        GatorTaxiRide gatorTaxiRide = new GatorTaxiRide(val1, val2, val3);
        this.redBlackTree.insert(gatorTaxiRide);
        System.out.println();
        redBlackTree.inorderTraversal();
    }


    private void execute(String inputFile) throws Exception {
        this.readOperations(inputFile);
        this.performOperations();
    }

    public static void main(String[] args) {
        gatorTaxi gt = new gatorTaxi();
        String inputFilePath = args[0];
        try {
//            RedBlackTree t = new RedBlackTree();
//            int[] arr = {1,4,6,3,5,7,8,2,9};
//            for(int i=0;i<9;i++)
//            {
//                t.insert(arr[i]);
//                System.out.println();
//                t.inorderTraversal();
//            }
//            t.printTree();
            gt.execute(inputFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}