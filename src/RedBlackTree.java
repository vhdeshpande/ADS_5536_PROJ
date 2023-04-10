import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    private RedBlackTreeNode root;

    private RedBlackTreeNode newNode;

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

    public RedBlackTreeNode cancelRide(Integer rideNumber)
    {
        if(this.root == null)
        {
            return null;
        }
        else
        {
            RedBlackTreeNode nodeToDelete = search(rideNumber);
            if(nodeToDelete.value.getRideNumber() != rideNumber){
                return null;
            }
            else
            {
                RedBlackTreeNode nodeCancelled = new RedBlackTreeNode(nodeToDelete.value, new MinHeapNode(nodeToDelete.value, nodeToDelete.ptrToMinHeapNode.index));
                deleteNode(nodeToDelete);
                return nodeCancelled;
            }
        }

    }

    public void deleteNode(RedBlackTreeNode node){
            // case 1: node is a leaf
            if (node.left == null && node.right == null) {
                if (node == root) {
                    root = null;
                    return;
                }
                if (node == null || node.color == Color.BLACK) {
                    fixDoubleBlack(node);
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
                        fixDoubleBlack(node);
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
            RedBlackTreeNode successor = getSuccessor(node);
            node.value = successor.value;
            MinHeapNode newNode = successor.ptrToMinHeapNode;
            newNode.ptrToRBTreeNode = node;
            node.ptrToMinHeapNode = newNode;
            deleteNode(successor);

//        this.root = deleteHelper(this.root, node);
//        RedBlackTreeNode nodeToReplace = replaceNode(node);
//        boolean uvBlack = ((nodeToReplace == null || nodeToReplace.color == Color.BLACK) && (node.color == Color.BLACK));
//        RedBlackTreeNode parent = node.parent;
//        if (nodeToReplace == null) {
//            if (node == this.root) {
//                this.root = null;
//            } else {
//                if (uvBlack) {
//                    fixDoubleBlack(node);
//                } else {
//                    if (node.sibling() != null)
//                        node.sibling().color = Color.RED;
//                }
//                if (node.isOnLeft()) {
//                    parent.left = null;
//                } else {
//                    parent.right = null;
//                }
//            }
//            return;
//        }
//        if (node.left == null || node.right == null) {
//            // v has 1 child
//            if (node == this.root) {
//                // v is root, assign the value of u to v, and delete u
//                node.value = nodeToReplace.value;
//                node.left = node.right = null;
//            } else {
//                // Detach v from tree and move u up
//                if (node.isOnLeft()) {
//                    parent.left = nodeToReplace;
//                } else {
//                    parent.right = nodeToReplace;
//                }
//                node.parent = parent;
//                if (uvBlack) {
//                    // u and v both black, fix double black at u
//                    fixDoubleBlack(nodeToReplace);
//                } else {
//                    // u or v red, color u black
//                    nodeToReplace.color = Color.BLACK;
//                }
//            }
//            return;
//        }
//
//        // v has 2 children, swap values with successor and recurse
//        swapValues(nodeToReplace, node);
//        deleteNode(nodeToReplace);
    }
    private RedBlackTreeNode getSuccessor(RedBlackTreeNode node) {
        RedBlackTreeNode successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor;
    }

    private RedBlackTreeNode deleteHelper(RedBlackTreeNode node, RedBlackTreeNode nodeToDelete) {
        if (node == null) {
            return null;
        }
        if (nodeToDelete.value.getRideNumber() < node.value.getRideNumber()) {
            // key is smaller than current node, search left subtree
            node.left = deleteHelper(node.left, nodeToDelete);
        } else if (nodeToDelete.value.getRideNumber() > node.value.getRideNumber()) {
            // key is greater than current node, search right subtree
            node.right = deleteHelper(node.right, nodeToDelete);
        } else {
            // found the node to delete
            if (node.left == null && node.right == null) {
                // case 1: node to be deleted has no children
                if (node.color == Color.BLACK) {
                    fixDoubleBlack(node);
                }
                return null;
            } else if (node.left == null || node.right == null) {
                // case 2: node to be deleted has one child
                RedBlackTreeNode child = node.left != null ? node.left : node.right;
                if (node.color == Color.BLACK) {
                    if (child.color == Color.RED) {
                        child.color = Color.BLACK;
                    } else {
                        fixDoubleBlack(child);
                    }
                }
                return child;
            } else {
                // case 3: node to be deleted has two children
                RedBlackTreeNode temp = minValueNode(node.right);
                node.value = temp.value;
                node.ptrToMinHeapNode = temp.ptrToMinHeapNode;
                node.right = deleteHelper(node.right, temp);
            }
        }
        return node;
    }
    private RedBlackTreeNode minValueNode(RedBlackTreeNode node) {
        RedBlackTreeNode current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void swapValues(RedBlackTreeNode node1, RedBlackTreeNode node2) {
        GatorTaxiRide temp;
        temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;

        MinHeapNode tempPtr = node1.getPtrToMinHeapNode();
        node1.setPtrToMinHeapNode(node2.getPtrToMinHeapNode());
        node2.setPtrToMinHeapNode(tempPtr);

    }
    public RedBlackTreeNode replaceNode(RedBlackTreeNode node){
        if (node.left != null && node.right != null) {
            RedBlackTreeNode curr = node.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
        if (node.left != null) {
            return node.left;
        }
        if (node.right != null) {
            return node.right;
        }
        return null;
    }

//    public RedBlackTreeNode fixDoubleBlack(RedBlackTreeNode node) {
//        if (node == root) {
//            // Reached the root node, simply return
//            return;
//        }
//
//        RedBlackNode sibling = node.getSibling();
//        RedBlackNode parent = node.parent;
//
//        if (sibling == null) {
//            // No sibling, double black pushed up
//            fixDoubleBlack(parent);
//        } else {
//            if (sibling.isRed) {
//                // Sibling is red
//                parent.isRed = true;
//                sibling.isRed = false;
//                if (isLeftChild(sibling)) {
//                    // left case
//                    rightRotate(parent);
//                } else {
//                    // right case
//                    leftRotate(parent);
//                }
//                fixDoubleBlack(node);
//            } else {
//                // Sibling is black
//                if ((sibling.left != null && sibling.left.isRed) || (sibling.right != null && sibling.right.isRed)) {
//                    // At least one red child
//                    if (sibling.left != null && sibling.left.isRed) {
//                        if (isLeftChild(sibling)) {
//                            // left left
//                            sibling.left.isRed = sibling.isRed;
//                            sibling.isRed = parent.isRed;
//                            rightRotate(parent);
//                        } else {
//                            // right left
//                            sibling.left.isRed = parent.isRed;
//                            rightRotate(sibling);
//                            leftRotate(parent);
//                        }
//                    } else {
//                        if (isLeftChild(sibling)) {
//                            // left right
//                            sibling.right.isRed = parent.isRed;
//                            leftRotate(sibling);
//                            rightRotate(parent);
//                        } else {
//                            // right right
//                            sibling.right.isRed = sibling.isRed;
//                            sibling.isRed = parent.isRed;
//                            leftRotate(parent);
//                        }
//                    }
//                    parent.isRed = false;
//                } else {
//                    // 2 black children
//                    sibling.isRed = true;
//                    if (!parent.isRed) {
//                        fixDoubleBlack(parent);
//                    } else {
//                        parent.isRed = false;
//                    }
//                }
//            }
//        }
//        return node;
//    }
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
    private void fixDoubleBlack(RedBlackTreeNode node) {
        if (node == root) {
            // Reached root node
            return;
        }

        RedBlackTreeNode sibling = node.sibling();
        RedBlackTreeNode parent = node.parent;

        if (sibling == null) {
            // Sibling is null, double black pushed up to parent
            fixDoubleBlack(parent);
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
                        fixDoubleBlack(parent);
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
                fixDoubleBlack(node);
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
