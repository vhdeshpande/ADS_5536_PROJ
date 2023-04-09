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
        MinHeapNode temp = minHeap[index1];
        minHeap[index1] = minHeap[index2];
        minHeap[index2] = temp;
        minHeap[index1].index = index1;
        minHeap[index2].index = index2;
    }

    public MinHeapNode removeMin() {
        if (isEmpty()) {
            return null;
        }

        MinHeapNode minNode = minHeap[1];
        minHeap[1] = minHeap[size--];
        int current = 1;
        int child = current * 2;
        while (child <= size) {
            if (child + 1 <= size && compare(minHeap[child+1], minHeap[child]) < 0) {
                child++;
            }
            if (compare(minHeap[child], minHeap[current]) < 0) {
                swap(current, child);
            } else {
                break;
            }
            current = child;
            child = current * 2;
        }
        return minNode;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void print()
    {
        for (int i = 0; i < size; i++) {
            System.out.print("(" + minHeap[i].value.getRideNumber() + " " + minHeap[i].value.getRideCost() + " " + minHeap[i].value.getTripDuration() + ") ");
        }

    }

    public void deleteNode(MinHeapNode node) {
        // Find the index of the node to be deleted
        int index = -1;
        for (int i = 1; i <= size; i++) {
            if (minHeap[i].equals(node)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            // Node not found
            return;
        }

        // Replace the node with the last node in the heap
        minHeap[index] = minHeap[size];
        minHeap[size] = null;
        size--;

        // Perform a sift-up or sift-down operation to restore the heap property
        if (index == 1 || compare(minHeap[index], minHeap[getParentIndex(index)]) >= 0) {
            siftDown(index);
        } else {
            siftUp(index);
        }
    }

    private void siftDown(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;

        // Find the smallest of the three nodes: the current node and its two children
        if (left < size && compare(minHeap[left], minHeap[smallest]) < 0) {
            smallest = left;
        }
        if (right < size && compare(minHeap[right], minHeap[smallest]) < 0) {
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
