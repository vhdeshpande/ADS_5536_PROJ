import java.util.ArrayList;
import java.util.List;

public class gatorTaxi {

    public static final String NULL_STRING = "NULL";

    RedBlackTree redBlackTree = new RedBlackTree();;
    MinHeap minHeap = new MinHeap(100);
    List<Operation> operations; //List to store operations read from file
    ResultWriter resultWriter; //Output file writer
    InputFileReader fileReader;//input file reader

    public gatorTaxi() {
        this.operations = new ArrayList<>();
        this.resultWriter = new ResultWriter();
        this.fileReader = new InputFileReader();
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
        this.operations = fileReader.getOperationsFromFile(filename);
    }

    private void performOperations() {
        for(Operation operation: operations)
            switch (operation.getOperationCode()) {
                case Print:
//                    if(operation.getVal2() == null)
//                    {
//                        this.print(operation.getVal1());
//                    }
//                    else
//                    {
//                        this.print(operation.getVal1(), operation.getVal2());
//                    }
                    break;
                case GetNextRide:
//                    this.getNextRide();
                    break;
                case Insert:
                    this.insert(operation.getVal1(), operation.getVal2(), operation.getVal3());
                    break;
                case CancelRide:
//                    this.cancelRide(operation.getVal1());
                    break;
                case UpdateTrip:
//                    this.updateTrip(operation.getVal1(), operation.getVal2());
                    break;

            }
//            this.writeResult(op.toString());
        closeWriter();
    }

    private void updateTrip(Integer rideNumber, Integer newTripDuration) {
        System.out.println("\n---Update Trip---");
        RedBlackTreeNode node = this.redBlackTree.search(rideNumber);
        Integer oldTripDuration = node.value.getTripDuration();
        if(newTripDuration <= oldTripDuration)
        {
            node.value.setTripDuration(newTripDuration);
        }
        else if(oldTripDuration < newTripDuration && (2 * oldTripDuration) >= newTripDuration)
        {
            cancelRide(rideNumber);
            insert(rideNumber, node.value.getRideCost() + 10, newTripDuration);
        }
        else if(newTripDuration > (2 * oldTripDuration))
        {
            cancelRide(rideNumber);
        }
    }

    private void getNextRide() {
        System.out.println("\n---Get Next Ride---");
        MinHeapNode node = this.minHeap.removeMin();
        if (node != null){
            System.out.print("(" + node.value.getRideNumber() + ", " + node.value.getRideCost() + ", " + node.value.getTripDuration() + ") ");
//            this.redBlackTree.deleteNode(node.ptrToRBTreeNode);
        }
        else
        {
            System.out.println("No active ride requests");
        }
    }

    private void print(Integer rideNumber) {
        System.out.println("\n---Print---");
        RedBlackTreeNode node = this.redBlackTree.search(rideNumber);

        if (node != null){
            System.out.print("(" + node.value.getRideNumber() + ", " + node.value.getRideCost() + ", " + node.value.getTripDuration() + ") ");
        }
        else
        {
            System.out.print("(" + 0 + ", " + 0 + ", " + 0 + ") ");
        }
    }

    private void print(Integer rideNumber1, Integer rideNumber2) {
        System.out.println("\n---Print Range Search---");
        List<RedBlackTreeNode> result = this.redBlackTree.rangeSearch(rideNumber1, rideNumber2);
        for (RedBlackTreeNode node : result){
            if (node != null){
                System.out.print("(" + node.value.getRideNumber() + ", " + node.value.getRideCost() + ", " + node.value.getTripDuration() + ") ");
            }
        }
    }

    private void cancelRide(Integer rideNumber) {
        System.out.println("\n---Cancel---");
        RedBlackTreeNode nodeToDelete = this.redBlackTree.cancelRide(rideNumber);
//        this.minHeap.deleteNode(nodeToDelete.getPtrToMinHeapNode());
        System.out.println();
        redBlackTree.inorderTraversal();
    }

    private void insert(int rideNumber, int rideCost, int tripDuration) {
        System.out.println("\n---Insert---");
        GatorTaxiRide gatorTaxiRide = new GatorTaxiRide(rideNumber, rideCost, tripDuration);

        RedBlackTreeNode rbTreeNewNode = this.redBlackTree.insert(gatorTaxiRide);
        if(rbTreeNewNode == null)
        {
            this.writeResult("Duplicate RideNumber");
            System.out.println("Duplicate RideNumber");
            return;
        }

        MinHeapNode minHeapNewNode = this.minHeap.insert(gatorTaxiRide);

        rbTreeNewNode.setPtrToMinHeapNode(minHeapNewNode);
        minHeapNewNode.setPtrToRBTreeNode(rbTreeNewNode);
        System.out.println("\n---RBT---");
        redBlackTree.inorderTraversal();

        System.out.println("\n---MH---");
        minHeap.print();
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