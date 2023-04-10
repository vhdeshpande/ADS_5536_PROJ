public class MinHeap {
    private MinHeapNode[] minHeap;
    private int size;
    private int capacity;

    public MinHeap(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.minHeap = new MinHeapNode[this.capacity+1];
    }

    public MinHeapNode insert(GatorTaxiRide gatorTaxiRide) {
        if (isFull()) {
            return null;
        }
        MinHeapNode newNode = new MinHeapNode(gatorTaxiRide, size);
        minHeap[size] = newNode;
        siftUp(size);
        size++;
        return newNode;
    }
    public boolean isFull() {
        return size == minHeap.length - 1;
    }
    private int compare(MinHeapNode node1, MinHeapNode node2) {
        if (node1.value.getRideCost() < node2.value.getRideCost()) {
            return -1;
        } else if (node1.value.getRideCost() > node2.value.getRideCost()) {
            return 1;
        } else {
            if (node1.value.getTripDuration() < node2.value.getTripDuration()) {
                return -1;
            } else if (node1.value.getTripDuration() > node2.value.getTripDuration()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private void swap(int index1, int index2) {
        MinHeapNode temp = this.minHeap[index1];
        this.minHeap[index1] = this.minHeap[index2];
        this.minHeap[index2] = temp;
        this.minHeap[index1].index = index1;
        this.minHeap[index2].index = index2;
    }

    public MinHeapNode removeMin() {
        if (isEmpty()) {
            return null;
        }
        MinHeapNode minNode = new MinHeapNode(this.minHeap[0].value, 0, this.minHeap[0].getPtrToRBTreeNode());
        this.minHeap[0] = this.minHeap[size-1];
        this.minHeap[0].setIndex(0);
        this.minHeap[size-1] = null;
        this.size--;
        siftDown(0);
        return minNode;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void print()
    {
        for (int i = 0; i < size; i++) {
            System.out.print("(" + this.minHeap[i].value.getRideNumber() + " " + this.minHeap[i].value.getRideCost() + " " + minHeap[i].value.getTripDuration() + ") ");
        }

    }

    public void deleteNode(MinHeapNode node) {
        // Find the index of the node to be deleted
        int index = node.getIndex();
        // Replace the node with the last node in the heap
        this.minHeap[index] = this.minHeap[this.size-1];
        this.minHeap[index].setIndex(index);
        this.minHeap[this.size-1] = null;
        this.size--;

        // Perform a sift-up or sift-down operation to restore the heap property
        if(this.minHeap[index] != null){
            if (index == 0 || compare(this.minHeap[index], this.minHeap[getParentIndex(index)]) >= 0) {
                siftDown(index);
            } else {
                siftUp(index);
            }
        }
    }

    private void siftDown(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;

        // Find the smallest of the three nodes: the current node and its two children
        if (left < size && compare(this.minHeap[left], this.minHeap[smallest]) < 0) {
            smallest = left;
        }
        if (right < size && compare(this.minHeap[right], this.minHeap[smallest]) < 0) {
            smallest = right;
        }

        // If the current node is not the smallest, swap it with the smallest and sift down again
        if (smallest != index) {
            swap(index, smallest);
            siftDown(smallest);
        }
    }

    // Sift up a node at the given index until its parent is smaller than it
    private void siftUp(int index) {
        if (index == 0)
        {
            return; // Root node, stop recursion
        }

        int parent = (index - 1) / 2;

        // If the parent is greater than the current node, swap them and sift up again
        if (compare(minHeap[index], minHeap[parent]) < 0) {
            swap(index, parent);
            siftUp(parent);
        }
    }

    private MinHeapNode parent(int index) {
        return minHeap[getParentIndex(index)];
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }
}
