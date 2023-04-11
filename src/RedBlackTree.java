import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    private RedBlackTreeNode root;

    private Boolean isDuplicateKey = false;

    public RedBlackTree(){
        super();
        root = null;
    }

//    Left rotation
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
//    right rotation
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
    //function to print inorder traversal
    public void inorderTraversal()
    {
        inorderTraversalHelper(this.root);
    }

    private void printTreeHelper(RedBlackTreeNode root, int space)
    {
        int i;
        if(root != null)
        {
            space = space + 10;
            printTreeHelper(root.getRight(), space);
            System.out.printf("\n");
            for ( i = 10; i < space; i++)
            {
                System.out.printf(" ");
            }
            System.out.printf("%d", root.getValue());
            System.out.printf("\n");
            printTreeHelper(root.getLeft(), space);
        }
    }
    // function to print the tree.
    public void printTree()
    {
        printTreeHelper(this.root, 0);
    }

    public RedBlackTreeNode search(int n) {
        RedBlackTreeNode curr = this.root;
        while (curr != null) {
            if (n < curr.getValue().getRideNumber()) {
                if (curr.getLeft() == null)
                    break;
                else
                    curr = curr.getLeft();
            } else if (n == curr.getValue().getRideNumber()) {
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

    public void deleteNode(RedBlackTreeNode node){
            // case 1: node is a leaf
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

            // case 2: node has only one child
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

            // case 3: node has two children
            RedBlackTreeNode successor = getInorderSuccessor(node);
            node.setValue(successor.getValue());
            MinHeapNode newNode = successor.getPtrToMinHeapNode();
            newNode.setPtrToRBTreeNode(node);
            node.setPtrToMinHeapNode(newNode);
            deleteNode(successor);
    }
    private RedBlackTreeNode getInorderSuccessor(RedBlackTreeNode node) {
        RedBlackTreeNode successor = node.getRight();
        while (successor.getLeft() != null) {
            successor = successor.getLeft();
        }
        return successor;
    }

    private RedBlackTreeNode minValueNode(RedBlackTreeNode node) {
        RedBlackTreeNode current = node;
        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    private boolean isBlack(RedBlackTreeNode node) {
        return node == null || node.getColor() == Color.BLACK;
    }

    private boolean isRed(RedBlackTreeNode node) {
        return node != null && node.getColor() == Color.RED;
    }

    private boolean isRightChild(RedBlackTreeNode node) {
        return node == node.getParent().getRight();
    }
    private boolean hasRedChild(RedBlackTreeNode node) {
        return (node.getLeft() != null && node.getLeft().getColor() == Color.RED) || (node.getRight() != null && node.getRight().getColor() == Color.RED);
    }
    private void handleDoubleBlackConflict(RedBlackTreeNode node) {
        if (node == root) {
            // Reached root node
            return;
        }

        RedBlackTreeNode sibling = node.sibling();
        RedBlackTreeNode parent = node.getParent();

        if (sibling == null) {
            // Sibling is null, double black pushed up to parent
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
                    // Sibling has two black children, double black pushed up to sibling
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

    public List<RedBlackTreeNode> rangeSearch(int a, int b) {
        List<RedBlackTreeNode> result = new ArrayList<>();
        rangeSearch(this.root, a, b, result);
        return result;
    }

    private void rangeSearch(RedBlackTreeNode node, int a, int b, List<RedBlackTreeNode> result) {
        if (node == null) {
            return;
        }

        if (node.getValue().getRideNumber() > a) {
            rangeSearch(node.getLeft(), a, b, result);
        }

        if (node.getValue().getRideNumber() >= a && node.getValue().getRideNumber() <= b) {
            result.add(node);
        }

        if (node.getValue().getRideNumber() < b) {
            rangeSearch(node.getRight(), a, b, result);
        }
    }

}
