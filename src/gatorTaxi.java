import java.util.ArrayList;
import java.util.List;

public class gatorTaxi {

    RedBlackTree redBlackTree = new RedBlackTree();

    MinHeap minHeap = new MinHeap(100);

    //Operations list read from the input file
    List<Operation> operations;

    //Output file writer
    GatorTaxiRideOutputWriter gatorTaxiRideOutputWriter;

    //input file reader
    GatorTaxiRideInputReader fileReader;

    public gatorTaxi() {
        this.operations = new ArrayList<>();
        this.gatorTaxiRideOutputWriter = new GatorTaxiRideOutputWriter();
        this.fileReader = new GatorTaxiRideInputReader();
    }

    private void writeResult(String text) {
        try {
            this.gatorTaxiRideOutputWriter.writeToFile(text);
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
        this.operations = fileReader.getOperationsFromFile(fileName);
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
        if(node != null && node.value.getRideNumber() == rideNumber){
            Integer oldTripDuration = node.value.getTripDuration();
            Integer oldRideCost = node.value.getRideCost();
            if(newTripDuration <= oldTripDuration)
            {
                node.value.setTripDuration(newTripDuration);
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
            System.out.print("(" + node.value.getRideNumber() + ", " + node.value.getRideCost() + ", " + node.value.getTripDuration() + ") ");
            this.redBlackTree.deleteNode(node.getPtrToRBTreeNode());
            this.writeResult(getGatorTaxiRideString(node.value));
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
        if (node != null && node.value.getRideNumber() == rideNumber){
            System.out.print("(" + node.value.getRideNumber() + ", " + node.value.getRideCost() + ", " + node.value.getTripDuration() + ") ");
            this.writeResult(getGatorTaxiRideString(node.value));
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
                System.out.print("(" + node.value.getRideNumber() + ", " + node.value.getRideCost() + ", " + node.value.getTripDuration() + ") ");
                res = res + getGatorTaxiRideString(node.value);
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
        if(nodeToDelete.value.getRideNumber() == rideNumber){
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

    public String getGatorTaxiRideString(GatorTaxiRide gatorTaxiRide){
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