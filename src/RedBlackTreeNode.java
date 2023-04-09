public class RedBlackTreeNode {

    public GatorTaxiRide value;
    public RedBlackTreeNode parent;
    public RedBlackTreeNode left;
    public RedBlackTreeNode right;

    public Color color;

    public MinHeapNode getPtrToMinHeapNode() {
        return ptrToMinHeapNode;
    }

    public void setPtrToMinHeapNode(MinHeapNode ptrToMinHeapNode) {
        this.ptrToMinHeapNode = ptrToMinHeapNode;
    }

    public MinHeapNode ptrToMinHeapNode;

    RedBlackTreeNode(GatorTaxiRide value){
        super();
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = Color.RED;
    }

    public RedBlackTreeNode sibling() {
        // sibling null if no parent
        if (parent == null)
            return null;

        if (isOnLeft())
            return parent.right;

        return parent.left;
    }

    public Boolean isOnLeft()
    {
        return this == parent.left;
    }
    public Boolean hasRedChild() {
        return (left != null && left.color == Color.RED) ||
                (right != null && right.color == Color.RED);
    }
}
