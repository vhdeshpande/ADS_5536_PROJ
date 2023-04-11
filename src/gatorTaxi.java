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
    private List<GatorTaxiOperation> gatorTaxiOperations;

    /**
     * Output file writer for gator taxi ride
     */
    private GatorTaxiOutputWriter gatorTaxiOutputWriter;

    /**
     * Input file reader for gator taxi ride
     */
    private GatorTaxiInputReader gatorTaxiInputReader;

    private static final String NO_RIDE_FOUND = "(0,0,0)";

    public gatorTaxi() {
        this.gatorTaxiOperations = new ArrayList<>();
        this.gatorTaxiOutputWriter = new GatorTaxiOutputWriter();
        this.gatorTaxiInputReader = new GatorTaxiInputReader();
    }

    /**
     * Writes result to the output file
     * @param output - output string to write
     */
    private void writeOutput(String output) {
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
        this.gatorTaxiOperations = gatorTaxiInputReader.getOperationListFromInputFile(inputFileName);
    }

    /**
     * Execute operations based on the list of operations read from the input file
     * @throws Exception
     */
    private void executeOperations() throws Exception {
        outerLoop:
        for(GatorTaxiOperation gatorTaxiOperation : gatorTaxiOperations) {
            switch (gatorTaxiOperation.getOperationCode()) {

                /**
                 * Execute print operation based on ride number
                 */
                case Print:
                    String ride = "";
                    if (gatorTaxiOperation.getInput2() == null) {
                        ride = this.print(gatorTaxiOperation.getInput1());
                    } else {
                        ride = this.print(gatorTaxiOperation.getInput1(), gatorTaxiOperation.getInput2());
                    }
                    this.writeOutput(ride);
                    break;

                /**
                 * Execute get next ride operation
                 */
                case GetNextRide:
                    String nextRide = this.getNextRide();
                    this.writeOutput(nextRide);
                    break;

                /**
                 * Execute insert operation - ride number, ride cost and trip duration
                 */
                case Insert:
                    Ride gtRide = this.insert(gatorTaxiOperation.getInput1(), gatorTaxiOperation.getInput2(), gatorTaxiOperation.getInput3());
                    if(gtRide == null){
                        this.writeOutput("Duplicate RideNumber");
                        break outerLoop;
                    }
                    break;

                /**
                 * Execute cancel ride operation based on ride number
                 */
                case CancelRide:
                    this.cancelRide(gatorTaxiOperation.getInput1());
                    break;

                /**
                 * Execute update ride operation based on ride number and update the trip duration
                 */
                case UpdateTrip:
                    this.updateTrip(gatorTaxiOperation.getInput1(), gatorTaxiOperation.getInput2());
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
    }

    /**
     * Get the next ride based on the minimum ride cost, remove the minimum value node from the min heap
     * and red-black tree
     * @return gator ride string if found
     */
    private String getNextRide() {
        MinHeapNode node = this.minHeap.getMinNode();
        String noActiveRideMsg = "No active ride requests";

        /**
         * If there are no ride, return no active ride requests msg
         */
        if (node != null){
            this.redBlackTree.deleteNode(node.getPtrToRBTreeNode());
            return node.getValue().toString();
        }
        else
        {
            return noActiveRideMsg;
        }

    }

    /**
     * Print ride, search ride based on the ride number in the red-black tree
     * @param rideNumber - ride number
     * @return gator ride string if found
     */
    private String print(Integer rideNumber) {
        RedBlackTreeNode node = this.redBlackTree.search(rideNumber);
        if (node != null && node.getValue().getRideNumber() == rideNumber){
            return node.getValue().toString();
        }
        else
        {
            return NO_RIDE_FOUND;
        }
    }

    /**
     * Print ride based on the range between ride number 1 and ride number 2
     * @param rideNumber1 - ride number lower bound
     * @param rideNumber2 - ride number upper bound
     * @return gator ride string if found
     */
    private String print(Integer rideNumber1, Integer rideNumber2) {
        List<RedBlackTreeNode> result = this.redBlackTree.rangeSearch(rideNumber1, rideNumber2);

        if(result.size() == 0){
            return NO_RIDE_FOUND;
        }
        String res = "";
        Integer count = result.size()-1;
        for (RedBlackTreeNode node : result){
            if (node != null)
            {
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
        return res;

    }

    /**
     * Cancel ride, delete node from min heap and red-black tree
     * @param rideNumber - ride number
     */
    private void cancelRide(Integer rideNumber) {
        RedBlackTreeNode nodeToDelete = this.redBlackTree.search(rideNumber);
        if(nodeToDelete.getValue().getRideNumber() == rideNumber){
            MinHeapNode minHeapNodeToDelete = nodeToDelete.getPtrToMinHeapNode();
            this.minHeap.deleteNode(minHeapNodeToDelete);
            this.redBlackTree.deleteNode(nodeToDelete);
        }
    }

    /**
     * Insert ride in min heap and red-black tree
     * @param rideNumber - ride number
     * @param rideCost - ride cost
     * @param tripDuration - trip duration
     * @return GatorTaxiRide object if the node was successfully inserted else return null
     */
    private Ride insert(Integer rideNumber, Integer rideCost, Integer tripDuration) {
        Ride ride = new Ride(rideNumber, rideCost, tripDuration);

        RedBlackTreeNode rbTreeNewNode = this.redBlackTree.insert(ride);
        if(rbTreeNewNode == null)
        {
            return null;
        }
        MinHeapNode minHeapNewNode = this.minHeap.insert(ride);

        //Update pointers to the nodes
        rbTreeNewNode.setPtrToMinHeapNode(minHeapNewNode);
        minHeapNewNode.setPtrToRBTreeNode(rbTreeNewNode);
        return ride;
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