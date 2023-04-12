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
        this.root = null;
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
            this.root = rightChild;
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
            this.root = leftChild;
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
    public RedBlackTreeNode insert(Ride data) {
        this.isDuplicateKey = false;
        RedBlackTreeNode newNode = new RedBlackTreeNode(data);
        if(this.root == null)
        {
            this.root = newNode;
            this.root.setColor(Color.BLACK);
            return this.root;
        }
        else
        {
            redBlackTreeInsertUtils(this.root, newNode);
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
        while (node.getParent() != null && node.getParent().getColor() == Color.RED) {
            if (node.getParent() == node.getParent().getParent().getLeft()) {
                RedBlackTreeNode parSiblingNode = node.getParent().getParent().getRight();
                if (parSiblingNode != null && parSiblingNode.getColor() == Color.RED) {
                    node.getParent().setColor(Color.BLACK);
                    parSiblingNode.setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    node = node.getParent().getParent();
                }
                else {
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
            else {
                RedBlackTreeNode parSiblingNode = node.getParent().getParent().getLeft();
                if (parSiblingNode != null && parSiblingNode.getColor() == Color.RED) {
                    node.getParent().setColor(Color.BLACK);
                    parSiblingNode.setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
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
     * @param node - current node of the subtree
     * @param newNode - node to insert in the red-black tree
     * @return node - current subtree node node
     */
    private RedBlackTreeNode redBlackTreeInsertUtils(RedBlackTreeNode node, RedBlackTreeNode newNode) {
        if(node == null) {
            return newNode;
        }
        else if(node.getValue().getRideNumber() == newNode.getValue().getRideNumber()) {
            this.isDuplicateKey = true;
            return null;
        }
        else if(newNode.getValue().getRideNumber() < node.getValue().getRideNumber()) {
            node.setLeft(redBlackTreeInsertUtils(node.getLeft(), newNode));
            if(node.getLeft() != null){
                node.getLeft().setParent(node);
            }
            return node;
        }
        else {
            node.setRight(redBlackTreeInsertUtils(node.getRight(), newNode));
            if(node.getRight() != null){
                node.getRight().setParent(node);
            }
            return node;
        }
    }

    /**
     * Search in the red-black tree based on ride number
     * @param rideNumber - ride number for the node to search
     * @return node - the red-black tree node if found otherwise returns null
     */
    public RedBlackTreeNode redBlackTreeSearch(Integer rideNumber) {
        RedBlackTreeNode currNode = this.root;
        while (currNode != null) {
            if (rideNumber < currNode.getValue().getRideNumber()) {
                if (currNode.getLeft() == null)
                    break;
                else
                    currNode = currNode.getLeft();
            } else if (rideNumber == currNode.getValue().getRideNumber()) {
                break;
            } else {
                if (currNode.getRight() == null)
                    break;
                else
                    currNode = currNode.getRight();
            }
        }

        return currNode;
    }

    /**
     * Function to delete the input node in red-black tree
     * @param node - node to delete
     */
    public void redBlackTreeDeleteNodeUtils(RedBlackTreeNode node){
            /**
             * If the node to delete is a leaf node
             */
            if (node.getLeft() == null && node.getRight() == null) {
                if (node == this.root) {
                    this.root = null;
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
                RedBlackTreeNode childNode = node.getLeft() == null ? node.getRight() : node.getLeft();
                if (node == this.root) {
                    childNode.setParent(null);
                    this.root = childNode;
                    this.root.setColor(Color.BLACK);
                    node = null;
                    return;
                }
                if (node == null || node.getColor() == Color.BLACK) {
                    if (childNode == null || childNode.getColor() == Color.BLACK) {
                        handleDoubleBlackConflict(node);
                    } else {
                        childNode.setColor(Color.BLACK);
                    }
                }
                if (node == node.getParent().getLeft()) {
                    node.getParent().setLeft(childNode);
                } else {
                    node.getParent().setRight(childNode);
                }
                childNode.setParent(node.getParent());
                node = null;
                return;
            }

            /**
             * If the node to delete has two child node
             */
            RedBlackTreeNode inorderSuccessor = getInorderSuccessor(node);
            node.setValue(inorderSuccessor.getValue());
            MinHeapNode newNode = inorderSuccessor.getPtrToMinHeapNode();
            newNode.setPtrToRBTreeNode(node);
            node.setPtrToMinHeapNode(newNode);
            redBlackTreeDeleteNodeUtils(inorderSuccessor);
    }

    /**
     * Get the inorder successor of the input node
     * @param node - red-black tree node
     * @return successor - returns the inorder successor for the node
     */
    private RedBlackTreeNode getInorderSuccessor(RedBlackTreeNode node) {
        RedBlackTreeNode inorderSucc = node.getRight();
        while (inorderSucc.getLeft() != null) {
            inorderSucc = inorderSucc.getLeft();
        }
        return inorderSucc;
    }

    /**
     * Returns true if the node is colored black
     * @param node - node
     * @return Boolean - returns true if the node is black
     */
    private boolean isNodeBlack(RedBlackTreeNode node) {
        return node == null || node.getColor() == Color.BLACK;
    }

    /**
     * Returns true if the node is colored red
     * @param node - node
     * @return Boolean - returns true if the node is red
     */
    private boolean isNodeRed(RedBlackTreeNode node) {
        return node != null && node.getColor() == Color.RED;
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

        RedBlackTreeNode siblingNode = node.sibling();
        RedBlackTreeNode parNode = node.getParent();

        /**
         * If the siblingNode of the node is null, double black is shifted to its parNode
         */
        if (siblingNode == null) {
            handleDoubleBlackConflict(parNode);
        } else {
            if (isNodeBlack(siblingNode)) {
                if (hasRedChild(siblingNode)) {
                    // Sibling has at least one red child
                    if (siblingNode.getLeft() != null && isNodeRed(siblingNode.getLeft())) {
                        if (siblingNode.isRightChild()) {
                            siblingNode.getLeft().setColor(siblingNode.getColor());
                            siblingNode.setColor(parNode.getColor());
                            rotateRight(siblingNode);
                        } else {
                            siblingNode.getLeft().setColor(parNode.getColor());
                            rotateRight(parNode);
                        }
                    } else {
                        if (siblingNode.isRightChild()) {
                            siblingNode.getRight().setColor(parNode.getColor());
                            rotateLeft(parNode);
                        } else {
                            siblingNode.getRight().setColor(siblingNode.getColor());
                            siblingNode.setColor(parNode.getColor());
                            rotateLeft(siblingNode);
                        }
                    }
                    parNode.setColor(Color.BLACK);
                } else {
                    // Sibling has two black children, double black is pushed up to siblingNode
                    siblingNode.setColor(Color.RED);
                    if (isNodeRed(parNode)) {
                        parNode.setColor(Color.BLACK);
                    } else {
                        handleDoubleBlackConflict(parNode);
                    }
                }
            } else {
                // Sibling is red, rotate to make siblingNode black
                siblingNode.setColor(Color.BLACK);
                parNode.setColor(Color.RED);
                if (siblingNode.isRightChild()) {
                    rotateLeft(parNode);
                    siblingNode = parNode.getRight();
                } else {
                    rotateRight(parNode);
                    siblingNode = parNode.getLeft();
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
    public List<RedBlackTreeNode> rangeSearch(Integer range1, Integer range2) {
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
    private void rangeSearch(RedBlackTreeNode node, Integer range1, Integer range2, List<RedBlackTreeNode> result) {
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
