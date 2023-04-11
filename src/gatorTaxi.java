import java.util.ArrayList;
import java.util.List;

/**
 * Gator taxi ride main class
 */
public class gatorTaxi {

    /**
     * Initialize red-black tree
     */
    private RedBlackTree redBlackTree = new RedBlackTree();

    /**
     * Initialize min heap with maximum capacity 200
     */
    private MinHeap minHeap = new MinHeap(2000);

    /**
     * List of operations from the input file
     */
    private List<Operation> operations;

    /**
     * Output file writer for gator taxi ride
     */
    private GatorTaxiOutputWriter gatorTaxiOutputWriter;

    /**
     * Input file reader for gator taxi ride
     */
    private GatorTaxiInputReader gatorTaxiInputReader;

    public gatorTaxi() {
        this.operations = new ArrayList<>();
        this.gatorTaxiOutputWriter = new GatorTaxiOutputWriter();
        this.gatorTaxiInputReader = new GatorTaxiInputReader();
    }

    /**
     * Writes result to the output file
     * @param output - output string to write
     */
    private void writeResult(String output) {
        try {
            this.gatorTaxiOutputWriter.writeOutputToFile(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the output writer
     */
    private void closeWriter() {
        try {
            this.gatorTaxiOutputWriter.closeOutputWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read input from the file
     * @param inputFileName - input file name
     * @throws Exception
     */
    private void readOperations(String inputFileName) throws Exception{
        this.operations = gatorTaxiInputReader.getOperationListFromInputFile(inputFileName);
    }

    /**
     * Execute operations based on the list of operations read from the input file
     * @throws Exception
     */
    private void executeOperations() throws Exception {
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

    /**
     * Update the ride with updated trip duration in red-black tree and min heap
     * @param rideNumber - ride number
     * @param newTripDuration - new trip duration
     */
    private void updateTrip(Integer rideNumber, Integer newTripDuration) {
        System.out.println("\n---Update Trip---");
        RedBlackTreeNode node = this.redBlackTree.search(rideNumber);
        if(node != null && node.getValue().getRideNumber() == rideNumber){
            Integer oldTripDuration = node.getValue().getTripDuration();
            Integer oldRideCost = node.getValue().getRideCost();
            /**
             * If the new trip duration is less than old duration, update the duration
             */
            if(newTripDuration <= oldTripDuration)
            {
                node.getValue().setTripDuration(newTripDuration);
            }
            /**
             * If the new trip duration is within the range of old trip duration and twice the old duration,
             * delete the node and insert a new node with new trip duration and ride cost + 10
             */
            else if(oldTripDuration < newTripDuration && (2 * oldTripDuration) >= newTripDuration)
            {
                MinHeapNode minHeapNodeToDelete = node.getPtrToMinHeapNode();
                this.minHeap.deleteNode(minHeapNodeToDelete);
                this.redBlackTree.deleteNode(node);
                insert(rideNumber, oldRideCost + 10, newTripDuration);
            }
            /**
             * If the new trip duration exceeds twice old duration, delete the node from min hep and red-black tree
             */
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

    /**
     * Get the next ride based on the minimum ride cost, remove the minimum value node from the min heap
     * and red-black tree
     */
    private void getNextRide() {
        System.out.println("\n---Get Next Ride---");
        MinHeapNode node = this.minHeap.removeMin();

        if (node != null){
            System.out.print("(" + node.getValue().getRideNumber() + ", " + node.getValue().getRideCost() + ", " + node.getValue().getTripDuration() + ") ");
            this.redBlackTree.deleteNode(node.getPtrToRBTreeNode());
            this.writeResult(node.getValue().toString());
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
            this.writeResult(node.getValue().toString());
        }
        else
        {
            System.out.print("(" + 0 + ", " + 0 + ", " + 0 + ") ");
            this.writeResult(new GatorTaxiRide(0,0,0).toString());
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
                res = res + node.getValue().toString();
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

    /**
     * Insert ride in min heap and red-black tree
     * @param rideNumber - ride number
     * @param rideCost - ride cost
     * @param tripDuration - trip duration
     * @return GatorTaxiRide object if the node was successfully inserted else return null
     */
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


    /**
     * Read and execute operations from the input file
     * @param inputFileName - input file name
     * @throws Exception
     */
    private void readAndExecuteOperations(String inputFileName) throws Exception {
        this.readOperations(inputFileName);
        this.executeOperations();
    }

    public static void main(String[] args) {
        gatorTaxi gt = new gatorTaxi();
        String inputFilePath = args[0];
        try {
            gt.readAndExecuteOperations(inputFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}