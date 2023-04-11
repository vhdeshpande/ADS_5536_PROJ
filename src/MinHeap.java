/**
 * Min Heap Class
 */
public class MinHeap {
    private MinHeapNode[] minHeap;

    //Min heap current size
    private int size;

    //Maximum min heap capacity
    private int capacity;

    public MinHeap(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.minHeap = new MinHeapNode[this.capacity+1];
    }

    /**
     * Insert node in min heap
     * @param gatorTaxiRide -gator taxi ride
     * @return new node
     */
    public MinHeapNode insert(GatorTaxiRide gatorTaxiRide) {
        if (isFull()) {
            return null;
        }
        MinHeapNode newNode = new MinHeapNode(gatorTaxiRide, size);
        minHeap[size] = newNode;
        heapifyUp(size);
        size++;
        return newNode;
    }

    /**
     * Check if the min heap is full
     * @return Boolean value if the min heap is full
     */
    private boolean isFull() {
        return size == minHeap.length - 1;
    }

    /**
     * Comparator function based on ride cost
     * @param node1 - node 1
     * @param node2 - node 2
     * @return -1,0,1 based on node1 and node2 values
     */
    private int compare(MinHeapNode node1, MinHeapNode node2) {
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
    private void swap(int index1, int index2) {
        MinHeapNode temp = this.minHeap[index1];
        this.minHeap[index1] = this.minHeap[index2];
        this.minHeap[index2] = temp;
        this.minHeap[index1].setIndex(index1);
        this.minHeap[index2].setIndex(index2);
    }

    public MinHeapNode removeMin() {
        if (isEmpty()) {
            return null;
        }
        MinHeapNode minNode = new MinHeapNode(this.minHeap[0].getValue(), 0, this.minHeap[0].getPtrToRBTreeNode());
        this.minHeap[0] = this.minHeap[size-1];
        this.minHeap[0].setIndex(0);
        this.minHeap[size-1] = null;
        this.size--;
        heapifyDown(0);
        return minNode;
    }


    /**
     * Check if the min heap is empty
     * @return Boolean value checking if the min heap is empty
     */
    private boolean isEmpty() {
        return size == 0;
    }

    public void print()
    {
        for (int i = 0; i < size; i++) {
            System.out.print("(" + this.minHeap[i].getValue().getRideNumber() + " " + this.minHeap[i].getValue().getRideCost() + " " + minHeap[i].getValue().getTripDuration() + ") ");
        }
    }

    /**
     * Delete node from the min heap
     * @param node - node to delete
     */
    public void deleteNode(MinHeapNode node) {
        // Get the index of the node to be deleted
        int index = node.getIndex();
        // Replace the node to delete with the last index node in the min heap and set the last node to null
        this.minHeap[index] = this.minHeap[this.size-1];
        this.minHeap[index].setIndex(index);
        this.minHeap[this.size-1] = null;
        this.size--;

        // Perform a heapify up or heapify down to maintain the heap property
        if(this.minHeap[index] != null){
            if (index == 0 || compare(this.minHeap[index], this.minHeap[getParentIndex(index)]) >= 0) {
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
    private void heapifyDown(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int minIndex = index;

        // Finds the minIndex of the node, left child and right child
        if (left < size && compare(this.minHeap[left], this.minHeap[minIndex]) < 0) {
            minIndex = left;
        }
        if (right < size && compare(this.minHeap[right], this.minHeap[minIndex]) < 0) {
            minIndex = right;
        }

        // If the current node is not the minimum index, swap it with the minIndex and heapify down
        if (minIndex != index) {
            swap(index, minIndex);
            heapifyDown(minIndex);
        }
    }

    /**
     * Heapify up the node at the index until its parent is smaller than the node
     * @param index
     */
    private void heapifyUp(int index) {
        //Root node reached
        if (index == 0) {
            return;
        }

        int parent = (index - 1) / 2;

        // If the parent is greater than the node, swap  and heapify up
        if (compare(minHeap[index], minHeap[parent]) < 0) {
            swap(index, parent);
            heapifyUp(parent);
        }
    }

    /**
     * Get parent index for node at index
     * @param index - index
     * @return parent node index
     */
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }
}
