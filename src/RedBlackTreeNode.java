public class RedBlackTreeNode {

    private GatorTaxiRide value;

    private RedBlackTreeNode parent;

    private RedBlackTreeNode left;

    private RedBlackTreeNode right;

    private Color color;

    /**
     * Reference to the correspinding node in the min heap
     */
    private MinHeapNode ptrToMinHeapNode;

    public MinHeapNode getPtrToMinHeapNode() {
        return ptrToMinHeapNode;
    }

    public void setPtrToMinHeapNode(MinHeapNode ptrToMinHeapNode) {
        this.ptrToMinHeapNode = ptrToMinHeapNode;
    }

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

    /**
     * Initialize red-black tree node
     * @param value
     */
    RedBlackTreeNode(GatorTaxiRide value){
        super();
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
        //Set initial color to RED
        this.color = Color.RED;
    }

    /**
     * Get sibling node
     * @return sibling or null if no parent
     */
    public RedBlackTreeNode sibling() {
        if (parent == null){
            return null;
        }
        if (isLeftChild()){
            return parent.right;
        }
        return parent.left;
    }

    /**
     * Checks if the node is the left child of its parent
     * @return Boolean - returns true if the node is the left child of its parent node
     */
    public Boolean isLeftChild()
    {
        return this == parent.left;
    }

    /**
     * Checks if the node is the right child of its parent
     * @return Boolean - returns true if the node is the right child of its parent node
     */
    public boolean isRightChild() {
        return this == parent.right;
    }

}
