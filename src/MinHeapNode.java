public class MinHeapNode {
    public Ride getValue() {
        return value;
    }

    public void setValue(Ride value) {
        this.value = value;
    }

    private Ride value;
    private Integer index;

    /**
     * Reference to the corresponding node in the red-black tree
     */
    private RedBlackTreeNode ptrToRBTreeNode;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public RedBlackTreeNode getPtrToRBTreeNode() {
        return ptrToRBTreeNode;
    }

    public void setPtrToRBTreeNode(RedBlackTreeNode ptrToRBTreeNode) {
        this.ptrToRBTreeNode = ptrToRBTreeNode;
    }

    public MinHeapNode(Ride value, Integer index) {
        this.value = value;
        this.index = index;
    }

    public MinHeapNode(Ride value, Integer index, RedBlackTreeNode ptrToRBTreeNode) {
        this.value = value;
        this.index = index;
        this.ptrToRBTreeNode = ptrToRBTreeNode;
    }
}
