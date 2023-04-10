public class MinHeapNode {
    public GatorTaxiRide value;
    public int index;
    public RedBlackTreeNode ptrToRBTreeNode;
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
