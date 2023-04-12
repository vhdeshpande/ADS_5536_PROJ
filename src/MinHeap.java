/**
 * Min Heap Class
 */
public class MinHeap {
    private MinHeapNode[] minHeap;

    //Min heap current size
    private Integer currCapacity;

    //Maximum min heap capacity
    private Integer maxCapacity;

    public MinHeap(Integer maxCapacity) {
        this.currCapacity = 0;
        this.maxCapacity = maxCapacity;
        this.minHeap = new MinHeapNode[this.maxCapacity +1];
    }

    /**
     * Insert node in min heap
     * @param ride -gator taxi ride
     * @return new node
     */
    public MinHeapNode insertNode(Ride ride) {
        if (hasReachedMaxCapacity()) {
            return null;
        }
        MinHeapNode newNode = new MinHeapNode(ride, currCapacity);
        minHeap[currCapacity] = newNode;
        heapifyUp(currCapacity);
        currCapacity++;
        return newNode;
    }

    /**
     * Check if the min heap is full
     * @return Boolean value if the min heap is full
     */
    private Boolean hasReachedMaxCapacity() {
        return currCapacity == minHeap.length - 1;
    }

    /**
     * Comparator function based on ride cost
     * @param node1 - node 1
     * @param node2 - node 2
     * @return -1,0,1 based on node1 and node2 values
     */
    private Integer compareNodes(MinHeapNode node1, MinHeapNode node2) {
        /**
         * Compares ride cost
         */
        if (node1.getValue().getRideCost() < node2.getValue().getRideCost()) {
            return -1;
        } else if (node1.getValue().getRideCost() > node2.getValue().getRideCost()) {
            return 1;
        } else {
            /**
             * Compares ride duration if nodes have the same ride cost
             */
            if (node1.getValue().getTripDuration() < node2.getValue().getTripDuration()) {
                return -1;
            } else if (node1.getValue().getTripDuration() > node2.getValue().getTripDuration()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Swap nodes
     * @param index1 - index
     * @param index2 - index
     */
    private void swapNodesBasedOnIndex(Integer index1, Integer index2) {
        MinHeapNode temp = this.minHeap[index1];
        this.minHeap[index1] = this.minHeap[index2];
        this.minHeap[index2] = temp;
        this.minHeap[index1].setIndex(index1);
        this.minHeap[index2].setIndex(index2);
    }

    /**
     * Get minimum gator ride node based on the ride cost and ride duration
     * @return min heap node
     */
    public MinHeapNode getMinNode() {
        if (isEmpty()) {
            return null;
        }
        //Remove the first node and replace it with the last node
        MinHeapNode minNode = new MinHeapNode(this.minHeap[0].getValue(), 0, this.minHeap[0].getPtrToRBTreeNode());
        this.minHeap[0] = this.minHeap[currCapacity -1];
        this.minHeap[0].setIndex(0);
        this.minHeap[currCapacity -1] = null;
        this.currCapacity--;
        //Heapify down to maintain the min heap property
        heapifyDown(0);
        return minNode;
    }


    /**
     * Check if the min heap is empty
     * @return Boolean value checking if the min heap is empty
     */
    private boolean isEmpty() {
        return currCapacity == 0;
    }

    /**
     * Delete node from the min heap
     * @param node - node to delete
     */
    public void deleteNode(MinHeapNode node) {
        // Get the index of the node to be deleted
        Integer index = node.getIndex();
        // Replace the node to delete with the last index node in the min heap and set the last node to null
        this.minHeap[index] = this.minHeap[this.currCapacity -1];
        this.minHeap[index].setIndex(index);
        this.minHeap[this.currCapacity -1] = null;
        this.currCapacity--;

        // Perform a heapify up or heapify down to maintain the heap property
        if(this.minHeap[index] != null){
            if (index == 0 || compareNodes(this.minHeap[index], this.minHeap[getParentIndex(index)]) >= 0) {
                heapifyDown(index);
            } else {
                heapifyUp(index);
            }
        }
    }

    /**
     * Heapify down the node at the index until its parent is smaller than the node
     * @param index - index
     */
    private void heapifyDown(Integer index) {
        Integer leftChild = index * 2 + 1;
        Integer rightChild = index * 2 + 2;
        Integer minimumIndex = index;

        // Finds the minimumIndex of the node, leftChild child and rightChild child
        if (leftChild < this.currCapacity && compareNodes(this.minHeap[leftChild], this.minHeap[minimumIndex]) < 0) {
            minimumIndex = leftChild;
        }
        if (rightChild < this.currCapacity && compareNodes(this.minHeap[rightChild], this.minHeap[minimumIndex]) < 0) {
            minimumIndex = rightChild;
        }

        // If the current node is not the minimum index, swap it with the minimumIndex and heapify down
        if (minimumIndex != index) {
            swapNodesBasedOnIndex(index, minimumIndex);
            heapifyDown(minimumIndex);
        }
    }

    /**
     * Heapify up the node at the index until its parent is smaller than the node
     * @param index
     */
    private void heapifyUp(Integer index) {
        //Root node reached
        if (index == 0) {
            return;
        }

        Integer par = (index - 1) / 2;

        // If the par is greater than the node, swap  and heapify up
        if (compareNodes(this.minHeap[index], this.minHeap[par]) < 0) {
            swapNodesBasedOnIndex(index, par);
            heapifyUp(par);
        }
    }

    /**
     * Get parent index for node at index
     * @param index - index
     * @return parent node index
     */
    private Integer getParentIndex(Integer index) {
        return (index - 1) / 2;
    }
}
