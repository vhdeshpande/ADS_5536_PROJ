public class RedBlackTreeNode {

    private GatorTaxiRide value;
    private RedBlackTreeNode parent;

    private RedBlackTreeNode left;

    private RedBlackTreeNode right;

    private Color color;

    public MinHeapNode getPtrToMinHeapNode() {
        return ptrToMinHeapNode;
    }

    public void setPtrToMinHeapNode(MinHeapNode ptrToMinHeapNode) {
        this.ptrToMinHeapNode = ptrToMinHeapNode;
    }

    private MinHeapNode ptrToMinHeapNode;

    public GatorTaxiRide getValue() {
        return value;
    }

    public void setValue(GatorTaxiRide value) {
        this.value = value;
    }

    public RedBlackTreeNode getParent() {
        return parent;
    }

    public void setParent(RedBlackTreeNode parent) {
        this.parent = parent;
    }

    public RedBlackTreeNode getLeft() {
        return left;
    }

    public void setLeft(RedBlackTreeNode left) {
        this.left = left;
    }

    public RedBlackTreeNode getRight() {
        return right;
    }

    public void setRight(RedBlackTreeNode right) {
        this.right = right;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

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
}
