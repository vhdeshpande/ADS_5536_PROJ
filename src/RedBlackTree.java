import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    private RedBlackTreeNode root;

    private Boolean isDuplicateKey = false;

    public RedBlackTree(){
        super();
        root = null;
    }

    public void rotateLeft(RedBlackTreeNode node)
    {
        RedBlackTreeNode rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }
    //this function performs right rotation
    public void rotateRight(RedBlackTreeNode node)
    {
        RedBlackTreeNode leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }


    public RedBlackTreeNode insert(GatorTaxiRide data) {
        isDuplicateKey = false;
        RedBlackTreeNode newNode = new RedBlackTreeNode(data);
        if(this.root == null)
        {
            this.root = newNode;
            this.root.color = Color.BLACK;
            return this.root;
        }
        else
        {
            redBlackTreeInsertHelper(this.root, newNode);
            if(this.isDuplicateKey){
                return null;
            }
            handleRRConflict(newNode);
            this.root.color = Color.BLACK;
            return newNode;
        }

    }

    private void handleRRConflict(RedBlackTreeNode node) {
        while (node.parent != null && node.parent.color == Color.RED)
        {
            if (node.parent == node.parent.parent.left)
            {
                RedBlackTreeNode uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == Color.RED)
                {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                }
                else
                {
                    if (node == node.parent.right)
                    {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rotateRight(node.parent.parent);
                }
            }
            else
            {
                RedBlackTreeNode uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == Color.RED)
                {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else
                {
                    if (node == node.parent.left)
                    {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
    }

    private RedBlackTreeNode redBlackTreeInsertHelper(RedBlackTreeNode root, RedBlackTreeNode newNode) {
        if(root == null)
        {
            return newNode;
        }
        else if(root.value.getRideNumber() == newNode.value.getRideNumber())
        {
            isDuplicateKey = true;
            return null;
        }
        else if(newNode.value.getRideNumber() < root.value.getRideNumber())
        {
            root.left = redBlackTreeInsertHelper(root.left, newNode);
            if(root.left != null){
                root.left.parent = root;
            }
            return root;
        }
        else
        {
            root.right = redBlackTreeInsertHelper(root.right, newNode);
            if(root.right != null){
                root.right.parent = root;
            }
            return root;
        }
    }

    void inorderTraversalHelper(RedBlackTreeNode node)
    {
        if(node!=null)
        {
            inorderTraversalHelper(node.left);
            System.out.printf("(%d %d %d (%s))", node.value.getRideNumber(), node.value.getRideCost(), node.value.getTripDuration(), node.color.toString());
            inorderTraversalHelper(node.right);
        }
    }
    //function to print inorder traversal
    public void inorderTraversal()
    {
        inorderTraversalHelper(this.root);
    }

    void printTreeHelper(RedBlackTreeNode root, int space)
    {
        int i;
        if(root != null)
        {
            space = space + 10;
            printTreeHelper(root.right, space);
            System.out.printf("\n");
            for ( i = 10; i < space; i++)
            {
                System.out.printf(" ");
            }
            System.out.printf("%d", root.value);
            System.out.printf("\n");
            printTreeHelper(root.left, space);
        }
    }
    // function to print the tree.
    public void printTree()
    {
        printTreeHelper(this.root, 0);
    }

    RedBlackTreeNode search(int n) {
        RedBlackTreeNode curr = this.root;
        while (curr != null) {
            if (n < curr.value.getRideNumber()) {
                if (curr.left == null)
                    break;
                else
                    curr = curr.left;
            } else if (n == curr.value.getRideNumber()) {
                break;
            } else {
                if (curr.right == null)
                    break;
                else
                    curr = curr.right;
            }
        }

        return curr;
    }

    public void deleteNode(RedBlackTreeNode node){
            // case 1: node is a leaf
            if (node.left == null && node.right == null) {
                if (node == root) {
                    root = null;
                    return;
                }
                if (node == null || node.color == Color.BLACK) {
                    handleDoubleBlackConflict(node);
                }
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                node = null;
                return;
            }

            // case 2: node has only one child
            if (node.left == null || node.right == null) {
                RedBlackTreeNode child = node.left == null ? node.right : node.left;
                if (node == root) {
                    child.parent = null;
                    root = child;
                    root.color = Color.BLACK;
                    node = null;
                    return;
                }
                if (node == null || node.color == Color.BLACK) {
                    if (child == null || child.color == Color.BLACK) {
                        handleDoubleBlackConflict(node);
                    } else {
                        child.color = Color.BLACK;
                    }
                }
                if (node == node.parent.left) {
                    node.parent.left = child;
                } else {
                    node.parent.right = child;
                }
                child.parent = node.parent;
                node = null;
                return;
            }

            // case 3: node has two children
            RedBlackTreeNode successor = getInorderSuccessor(node);
            node.value = successor.value;
            MinHeapNode newNode = successor.ptrToMinHeapNode;
            newNode.ptrToRBTreeNode = node;
            node.ptrToMinHeapNode = newNode;
            deleteNode(successor);
    }
    private RedBlackTreeNode getInorderSuccessor(RedBlackTreeNode node) {
        RedBlackTreeNode successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    private RedBlackTreeNode minValueNode(RedBlackTreeNode node) {
        RedBlackTreeNode current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private boolean isBlack(RedBlackTreeNode node) {
        return node == null || node.color == Color.BLACK;
    }

    private boolean isRed(RedBlackTreeNode node) {
        return node != null && node.color == Color.RED;
    }

    private boolean isLeftChild(RedBlackTreeNode node) {
        return node == node.parent.left;
    }

    private boolean isRightChild(RedBlackTreeNode node) {
        return node == node.parent.right;
    }
    private boolean hasRedChild(RedBlackTreeNode node) {
        return (node.left != null && node.left.color == Color.RED) || (node.right != null && node.right.color == Color.RED);
    }
    private void handleDoubleBlackConflict(RedBlackTreeNode node) {
        if (node == root) {
            // Reached root node
            return;
        }

        RedBlackTreeNode sibling = node.sibling();
        RedBlackTreeNode parent = node.parent;

        if (sibling == null) {
            // Sibling is null, double black pushed up to parent
            handleDoubleBlackConflict(parent);
        } else {
            if (isBlack(sibling)) {
                if (hasRedChild(sibling)) {
                    // Sibling has at least one red child
                    if (sibling.left != null && isRed(sibling.left)) {
                        if (isRightChild(sibling)) {
                            sibling.left.color = sibling.color;
                            sibling.color = parent.color;
                            rotateRight(sibling);
                        } else {
                            sibling.left.color = parent.color;
                            rotateRight(parent);
                        }
                    } else {
                        if (isRightChild(sibling)) {
                            sibling.right.color = parent.color;
                            rotateLeft(parent);
                        } else {
                            sibling.right.color = sibling.color;
                            sibling.color = parent.color;
                            rotateLeft(sibling);
                        }
                    }
                    parent.color = Color.BLACK;
                } else {
                    // Sibling has two black children, double black pushed up to sibling
                    sibling.color = Color.RED;
                    if (isRed(parent)) {
                        parent.color = Color.BLACK;
                    } else {
                        handleDoubleBlackConflict(parent);
                    }
                }
            } else {
                // Sibling is red, rotate to make sibling black
                sibling.color = Color.BLACK;
                parent.color = Color.RED;
                if (isRightChild(sibling)) {
                    rotateLeft(parent);
                    sibling = parent.right;
                } else {
                    rotateRight(parent);
                    sibling = parent.left;
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

        if (node.value.getRideNumber() > a) {
            rangeSearch(node.left, a, b, result);
        }

        if (node.value.getRideNumber() >= a && node.value.getRideNumber() <= b) {
            result.add(node);
        }

        if (node.value.getRideNumber() < b) {
            rangeSearch(node.right, a, b, result);
        }
    }


}
