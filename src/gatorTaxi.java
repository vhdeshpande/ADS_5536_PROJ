import java.util.ArrayList;
import java.util.List;

public class gatorTaxi {

//    Red black tree
    private RedBlackTree redBlackTree = new RedBlackTree();

//    Min Heap
    private MinHeap minHeap = new MinHeap(100);

//    Operations list read from the input file
    private List<Operation> operations;

//    Output file writer
    private GatorTaxiRideOutputWriter gatorTaxiRideOutputWriter;

//    input file reader
    private GatorTaxiRideInputReader gatorTaxiRideInputReader;

    public gatorTaxi() {
        this.operations = new ArrayList<>();
        this.gatorTaxiRideOutputWriter = new GatorTaxiRideOutputWriter();
        this.gatorTaxiRideInputReader = new GatorTaxiRideInputReader();
    }

    private void writeResult(String outputStr) {
        try {
            this.gatorTaxiRideOutputWriter.writeOutputToFile(outputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWriter() {
        try {
            this.gatorTaxiRideOutputWriter.closeWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readOperations(String fileName) throws Exception{
        this.operations = gatorTaxiRideInputReader.getOperationsFromFile(fileName);
    }

    private void performOperations() throws Exception {
        outerLoop:
        for(Operation operation: operations) {
//            this.writeResult(operation.toString());
            System.out.println("\n\n"+operation.toString());
            switch (operation.getOperationCode()) {

                case Print:
                    if (operation.getInput2() == null) {
                        this.print(operation.getInput1());
                    } else {
                        this.print(operation.getInput1(), operation.getInput2());
                    }
                    break;

                case GetNextRide:
                    this.getNextRide();
                    break;

                case Insert:
                    GatorTaxiRide gtRide = this.insert(operation.getInput1(), operation.getInput2(), operation.getInput3());
                    if(gtRide == null){
                        this.writeResult("Duplicate RideNumber");
                        System.out.println("Duplicate RideNumber");
                        break outerLoop;
                    }
                    break;

                case CancelRide:
                    this.cancelRide(operation.getInput1());
                    break;

                case UpdateTrip:
                    this.updateTrip(operation.getInput1(), operation.getInput2());
                    break;
            }
        }
        closeWriter();
    }

    private void updateTrip(Integer rideNumber, Integer newTripDuration) {
        System.out.println("\n---Update Trip---");
        RedBlackTreeNode node = this.redBlackTree.search(rideNumber);
        if(node != null && node.getValue().getRideNumber() == rideNumber){
            Integer oldTripDuration = node.getValue().getTripDuration();
            Integer oldRideCost = node.getValue().getRideCost();
            if(newTripDuration <= oldTripDuration)
            {
                node.getValue().setTripDuration(newTripDuration);
            }
            else if(oldTripDuration < newTripDuration && (2 * oldTripDuration) >= newTripDuration)
            {
                MinHeapNode minHeapNodeToDelete = node.getPtrToMinHeapNode();
                this.minHeap.deleteNode(minHeapNodeToDelete);
                this.redBlackTree.deleteNode(node);
                insert(rideNumber, oldRideCost + 10, newTripDuration);
            }
            else if(newTripDuration > (2 * oldTripDuration))
            {

                MinHeapNode minHeapNodeToDelete = node.getPtrToMinHeapNode();
                this.minHeap.deleteNode(minHeapNodeToDelete);
                this.redBlackTree.deleteNode(node);
            }
        }
        System.out.println("\n---RBT---");
        redBlackTree.inorderTraversal();

        System.out.println("\n---MH---");
        minHeap.print();

    }
    private void getNextRide() {
        System.out.println("\n---Get Next Ride---");
        MinHeapNode node = this.minHeap.removeMin();

        if (node != null){
            System.out.print("(" + node.getValue().getRideNumber() + ", " + node.getValue().getRideCost() + ", " + node.getValue().getTripDuration() + ") ");
            this.redBlackTree.deleteNode(node.getPtrToRBTreeNode());
            this.writeResult(getGatorTaxiRideString(node.getValue()));
        }
        else
        {
            this.writeResult("No active ride requests");
        }
        System.out.println("\n---RBT---");
        redBlackTree.inorderTraversal();

        System.out.println("\n---MH---");
        minHeap.print();
    }

    private void print(Integer rideNumber) {
        System.out.println("\n---Print---");
        RedBlackTreeNode node = this.redBlackTree.search(rideNumber);
        if (node != null && node.getValue().getRideNumber() == rideNumber){
            System.out.print("(" + node.getValue().getRideNumber() + ", " + node.getValue().getRideCost() + ", " + node.getValue().getTripDuration() + ") ");
            this.writeResult(getGatorTaxiRideString(node.getValue()));
        }
        else
        {
            System.out.print("(" + 0 + ", " + 0 + ", " + 0 + ") ");
            this.writeResult(getGatorTaxiRideString(new GatorTaxiRide(0,0,0)));
        }
    }

    private void print(Integer rideNumber1, Integer rideNumber2) {
        System.out.println("\n---Print Range Search---");
        List<RedBlackTreeNode> result = this.redBlackTree.rangeSearch(rideNumber1, rideNumber2);
        String res = "";
        int count = result.size()-1;
        if(result.size() == 0){
            res = res + "(" + 0 + ", " + 0 + ", " + 0 + ") ";
        }
        for (RedBlackTreeNode node : result){
            if (node != null)
            {
                System.out.print("(" + node.getValue().getRideNumber() + ", " + node.getValue().getRideCost() + ", " + node.getValue().getTripDuration() + ") ");
                res = res + getGatorTaxiRideString(node.getValue());
            }
            if(count != 0)
            {
                if(count != 0){
                    res = res + ",";
                }
            }
            count--;
        }
        this.writeResult(res);

    }

    private void cancelRide(Integer rideNumber) {
        System.out.println("\n---Cancel---");

        RedBlackTreeNode nodeToDelete = this.redBlackTree.search(rideNumber);
        if(nodeToDelete.getValue().getRideNumber() == rideNumber){
            MinHeapNode minHeapNodeToDelete = nodeToDelete.getPtrToMinHeapNode();
            this.minHeap.deleteNode(minHeapNodeToDelete);
            this.redBlackTree.deleteNode(nodeToDelete);
        }

        System.out.println("\n---RBT---");
        this.redBlackTree.inorderTraversal();

        System.out.println("\n---MH---");
        this.minHeap.print();
    }

    private GatorTaxiRide insert(int rideNumber, int rideCost, int tripDuration) {
        System.out.println("\n---Insert---");
        GatorTaxiRide gatorTaxiRide = new GatorTaxiRide(rideNumber, rideCost, tripDuration);

        RedBlackTreeNode rbTreeNewNode = this.redBlackTree.insert(gatorTaxiRide);
        if(rbTreeNewNode == null)
        {
            return null;
        }

        MinHeapNode minHeapNewNode = this.minHeap.insert(gatorTaxiRide);

        rbTreeNewNode.setPtrToMinHeapNode(minHeapNewNode);
        minHeapNewNode.setPtrToRBTreeNode(rbTreeNewNode);
        System.out.println("\n---RBT---");
        redBlackTree.inorderTraversal();

        System.out.println("\n---MH---");
        minHeap.print();
        return gatorTaxiRide;
    }

    private String getGatorTaxiRideString(GatorTaxiRide gatorTaxiRide){
        return "(" + gatorTaxiRide.getRideNumber() + "," + gatorTaxiRide.getRideCost() + "," + gatorTaxiRide.getTripDuration() + ")";
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