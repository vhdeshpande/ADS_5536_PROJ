import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    /**
     * Red-black tree root node
     */
    private RedBlackTreeNode root;

    /**
     * To check if the ride number being inserted is duplicate
     */
    private Boolean isDuplicateKey = false;

    public RedBlackTree(){
        super();
        root = null;
    }

    /**
     * Rotate left to fix imbalance
     * @param node node to rotate
     */
    private void rotateLeft(RedBlackTreeNode node)
    {
        RedBlackTreeNode rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(node);
        }
        rightChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = rightChild;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(rightChild);
        } else {
            node.getParent().setRight(rightChild);
        }
        rightChild.setLeft(node);
        node.setParent(rightChild);
    }

    /**
     * Rotate right to fix imbalance
     * @param node node to rotate
     */
    public void rotateRight(RedBlackTreeNode node)
    {
        RedBlackTreeNode leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(node);
        }
        leftChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = leftChild;
        } else if (node == node.getParent().getRight()) {
            node.getParent().setRight(leftChild);
        } else {
            node.getParent().setLeft(leftChild);
        }
        leftChild.setRight(node);
        node.setParent(leftChild);
    }

    /**
     * Insert node in the red-black tree
     * @param data - gator taxi ride data
     * @return newNode - inserted node
     */
    public RedBlackTreeNode insert(GatorTaxiRide data) {
        isDuplicateKey = false;
        RedBlackTreeNode newNode = new RedBlackTreeNode(data);
        if(this.root == null)
        {
            this.root = newNode;
            this.root.setColor(Color.BLACK);
            return this.root;
        }
        else
        {
            redBlackTreeInsertHelper(this.root, newNode);
            if(this.isDuplicateKey){
                return null;
            }
            handleRRConflict(newNode);
            this.root.setColor(Color.BLACK);
            return newNode;
        }

    }

    /**
     * Handle red-red conflict
     * @param node node to rotate
     */
    private void handleRRConflict(RedBlackTreeNode node) {
        while (node.getParent() != null && node.getParent().getColor() == Color.RED)
        {
            if (node.getParent() == node.getParent().getParent().getLeft())
            {
                RedBlackTreeNode uncle = node.getParent().getParent().getRight();
                if (uncle != null && uncle.getColor() == Color.RED)
                {
                    node.getParent().setColor(Color.BLACK);
                    uncle.setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    node = node.getParent().getParent();
                }
                else
                {
                    if (node == node.getParent().getRight())
                    {
                        node = node.getParent();
                        rotateLeft(node);
                    }
                    node.getParent().setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    rotateRight(node.getParent().getParent());
                }
            }
            else
            {
                RedBlackTreeNode uncle = node.getParent().getParent().getLeft();
                if (uncle != null && uncle.getColor() == Color.RED)
                {
                    node.getParent().setColor(Color.BLACK);
                    uncle.setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    node = node.getParent().getParent();
                } else
                {
                    if (node == node.getParent().getLeft())
                    {
                        node = node.getParent();
                        rotateRight(node);
                    }
                    node.getParent().setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    rotateLeft(node.getParent().getParent());
                }
            }
        }
    }

    /**
     * Helper function for inserting a node in the red-black tree
     * @param root - current root node of the subtree
     * @param newNode - node to insert in the red-black tree
     * @return node - current subtree root node
     */
    private RedBlackTreeNode redBlackTreeInsertHelper(RedBlackTreeNode root, RedBlackTreeNode newNode) {
        if(root == null)
        {
            return newNode;
        }
        else if(root.getValue().getRideNumber() == newNode.getValue().getRideNumber())
        {
            isDuplicateKey = true;
            return null;
        }
        else if(newNode.getValue().getRideNumber() < root.getValue().getRideNumber())
        {
            root.setLeft(redBlackTreeInsertHelper(root.getLeft(), newNode));
            if(root.getLeft() != null){
                root.getLeft().setParent(root);
            }
            return root;
        }
        else
        {
            root.setRight(redBlackTreeInsertHelper(root.getRight(), newNode));
            if(root.getRight() != null){
                root.getRight().setParent(root);
            }
            return root;
        }
    }

    private void inorderTraversalHelper(RedBlackTreeNode node)
    {
        if(node!=null)
        {
            inorderTraversalHelper(node.getLeft());
            System.out.printf("(%d %d %d (%s))", node.getValue().getRideNumber(), node.getValue().getRideCost(), node.getValue().getTripDuration(), node.getColor().toString());
            inorderTraversalHelper(node.getRight());
        }
    }

    public void inorderTraversal()
    {
        inorderTraversalHelper(this.root);
    }

    /**
     * Search in the red-black tree based on ride number
     * @param rideNumber - ride number for the node to search
     * @return node - the red-black tree node if found otherwise returns null
     */
    public RedBlackTreeNode search(int rideNumber) {
        RedBlackTreeNode curr = this.root;
        while (curr != null) {
            if (rideNumber < curr.getValue().getRideNumber()) {
                if (curr.getLeft() == null)
                    break;
                else
                    curr = curr.getLeft();
            } else if (rideNumber == curr.getValue().getRideNumber()) {
                break;
            } else {
                if (curr.getRight() == null)
                    break;
                else
                    curr = curr.getRight();
            }
        }

        return curr;
    }

    /**
     * Function to delete the input node in red-black tree
     * @param node - node to delete
     */
    public void deleteNode(RedBlackTreeNode node){
            // case 1: node is a leaf
            /**
             * If the node to delete is a leaf node
             */
            if (node.getLeft() == null && node.getRight() == null) {
                if (node == root) {
                    root = null;
                    return;
                }
                if (node == null || node.getColor() == Color.BLACK) {
                    handleDoubleBlackConflict(node);
                }
                if (node == node.getParent().getLeft()) {
                    node.getParent().setLeft(null);
                } else {
                    node.getParent().setRight(null);
                }
                node = null;
                return;
            }


            /**
             * If the node to delete has one child node
             */
            if (node.getLeft() == null || node.getRight() == null) {
                RedBlackTreeNode child = node.getLeft() == null ? node.getRight() : node.getLeft();
                if (node == root) {
                    child.setParent(null);
                    root = child;
                    root.setColor(Color.BLACK);
                    node = null;
                    return;
                }
                if (node == null || node.getColor() == Color.BLACK) {
                    if (child == null || child.getColor() == Color.BLACK) {
                        handleDoubleBlackConflict(node);
                    } else {
                        child.setColor(Color.BLACK);
                    }
                }
                if (node == node.getParent().getLeft()) {
                    node.getParent().setLeft(child);
                } else {
                    node.getParent().setRight(child);
                }
                child.setParent(node.getParent());
                node = null;
                return;
            }

            /**
             * If the node to delete has two child node
             */
            RedBlackTreeNode successor = getInorderSuccessor(node);
            node.setValue(successor.getValue());
            MinHeapNode newNode = successor.getPtrToMinHeapNode();
            newNode.setPtrToRBTreeNode(node);
            node.setPtrToMinHeapNode(newNode);
            deleteNode(successor);
    }

    /**
     * Get the inorder successor of the input node
     * @param node - red-black tree node
     * @return successor - returns the inorder successor for the node
     */
    private RedBlackTreeNode getInorderSuccessor(RedBlackTreeNode node) {
        RedBlackTreeNode successor = node.getRight();
        while (successor.getLeft() != null) {
            successor = successor.getLeft();
        }
        return successor;
    }

    /**
     * Returns true if the node is colored black
     * @param node - node
     * @return Boolean - returns true if the node is black
     */
    private boolean isBlack(RedBlackTreeNode node) {
        return node == null || node.getColor() == Color.BLACK;
    }

    /**
     * Returns true if the node is colored red
     * @param node - node
     * @return Boolean - returns true if the node is red
     */
    private boolean isRed(RedBlackTreeNode node) {
        return node != null && node.getColor() == Color.RED;
    }

    /**
     * Checks if the input node is the right child of its parent node
     * @param node - red-black tree node
     * @return Boolean - returns true if the node is the right child of its parent node
     */
    private boolean isRightChild(RedBlackTreeNode node) {
        return node == node.getParent().getRight();
    }
    private boolean hasRedChild(RedBlackTreeNode node) {
        return (node.getLeft() != null && node.getLeft().getColor() == Color.RED) || (node.getRight() != null && node.getRight().getColor() == Color.RED);
    }

    /**
     * Handles the double black conflict when a node is deleted from the red-black tree
     * @param node - node to be deleted
     */
    private void handleDoubleBlackConflict(RedBlackTreeNode node) {
        /**
         * Check if node is the root node
         */
        if (node == this.root) {
            return;
        }

        RedBlackTreeNode sibling = node.sibling();
        RedBlackTreeNode parent = node.getParent();

        /**
         * If the sibling of the node is null, double black is shifted to its parent
         */
        if (sibling == null) {
            handleDoubleBlackConflict(parent);
        } else {
            if (isBlack(sibling)) {
                if (hasRedChild(sibling)) {
                    // Sibling has at least one red child
                    if (sibling.getLeft() != null && isRed(sibling.getLeft())) {
                        if (isRightChild(sibling)) {
                            sibling.getLeft().setColor(sibling.getColor());
                            sibling.setColor(parent.getColor());
                            rotateRight(sibling);
                        } else {
                            sibling.getLeft().setColor(parent.getColor());
                            rotateRight(parent);
                        }
                    } else {
                        if (isRightChild(sibling)) {
                            sibling.getRight().setColor(parent.getColor());
                            rotateLeft(parent);
                        } else {
                            sibling.getRight().setColor(sibling.getColor());
                            sibling.setColor(parent.getColor());
                            rotateLeft(sibling);
                        }
                    }
                    parent.setColor(Color.BLACK);
                } else {
                    // Sibling has two black children, double black is pushed up to sibling
                    sibling.setColor(Color.RED);
                    if (isRed(parent)) {
                        parent.setColor(Color.BLACK);
                    } else {
                        handleDoubleBlackConflict(parent);
                    }
                }
            } else {
                // Sibling is red, rotate to make sibling black
                sibling.setColor(Color.BLACK);
                parent.setColor(Color.RED);
                if (isRightChild(sibling)) {
                    rotateLeft(parent);
                    sibling = parent.getRight();
                } else {
                    rotateRight(parent);
                    sibling = parent.getLeft();
                }
                handleDoubleBlackConflict(node);
            }
        }
    }

    /**
     * Range Search wrapper method
     * @param range1 - range lower bound
     * @param range2 - range upper bound
     * @return list of gator rides between the range specified
     */
    public List<RedBlackTreeNode> rangeSearch(int range1, int range2) {
        List<RedBlackTreeNode> result = new ArrayList<>();
        rangeSearch(this.root, range1, range2, result);
        return result;
    }

    /**
     * Range Search based on the ride number property
     * @param node - current node
     * @param range1 - range lower bound
     * @param range2 - range upper bound
     * @param result - list of gator rides between the range specified
     */
    private void rangeSearch(RedBlackTreeNode node, int range1, int range2, List<RedBlackTreeNode> result) {
        if (node == null) {
            return;
        }
        if (node.getValue().getRideNumber() > range1) {
            rangeSearch(node.getLeft(), range1, range2, result);
        }
        if (node.getValue().getRideNumber() >= range1 && node.getValue().getRideNumber() <= range2) {
            result.add(node);
        }
        if (node.getValue().getRideNumber() < range2) {
            rangeSearch(node.getRight(), range1, range2, result);
        }
    }

}
