public class MinHeapNode {
    public GatorTaxiRide getValue() {
        return value;
    }

    public void setValue(GatorTaxiRide value) {
        this.value = value;
    }

    private GatorTaxiRide value;
    private int index;
    private RedBlackTreeNode ptrToRBTreeNode;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public RedBlackTreeNode getPtrToRBTreeNode() {
        return ptrToRBTreeNode;
    }
    public void setPtrToRBTreeNode(RedBlackTreeNode ptrToRBTreeNode) {
        this.ptrToRBTreeNode = ptrToRBTreeNode;
    }
    public MinHeapNode(GatorTaxiRide value, int index) {
        this.value = value;
        this.index = index;
    }

    public MinHeapNode(GatorTaxiRide value, int index, RedBlackTreeNode ptrToRBTreeNode) {
        this.value = value;
        this.index = index;
        this.ptrToRBTreeNode = ptrToRBTreeNode;
    }
}
