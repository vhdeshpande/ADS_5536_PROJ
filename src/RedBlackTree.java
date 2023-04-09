import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    private RedBlackTreeNode root;

    private RedBlackTreeNode newNode;

    private Boolean isDuplicateKey = false;

    private boolean ll = false;
    private boolean rr = false;
    private boolean lr = false;
    private boolean rl = false;
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
                deleteNode(nodeToDelete);
                return nodeToDelete;
            }
        }

    }

    public void deleteNode(RedBlackTreeNode node){
        RedBlackTreeNode nodeToReplace = replaceNode(node);
        boolean uvBlack = ((nodeToReplace == null || nodeToReplace.color == Color.BLACK) && (node.color == Color.BLACK));
        RedBlackTreeNode parent = node.parent;
        if (nodeToReplace == null) {
            if (node == root) {
                root = null;
            } else {
                if (uvBlack) {
                    // u and v both black
                    // v is leaf, fix double black at v
                    fixDoubleBlack(node);
                } else {
                    // u or v is red
                    if (node.sibling() != null)
                        // sibling is not null, make it red"
                        node.sibling().color = Color.RED;
                }

                // delete v from the tree
                if (node.isOnLeft()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            return;
        }
        if (node.left == null || node.right == null) {
            // v has 1 child
            if (node == root) {
                // v is root, assign the value of u to v, and delete u
                node.value = nodeToReplace.value;
                node.left = node.right = null;
            } else {
                // Detach v from tree and move u up
                if (node.isOnLeft()) {
                    parent.left = nodeToReplace;
                } else {
                    parent.right = nodeToReplace;
                }
                node.parent = parent;
                if (uvBlack) {
                    // u and v both black, fix double black at u
                    fixDoubleBlack(nodeToReplace);
                } else {
                    // u or v red, color u black
                    nodeToReplace.color = Color.BLACK;
                }
            }
            return;
        }

        // v has 2 children, swap values with successor and recurse
        swapValues(nodeToReplace, node);
        swapValues(nodeToReplace, node);
        deleteNode(nodeToReplace);
    }

    public void swapValues(RedBlackTreeNode node1, RedBlackTreeNode node2) {
        GatorTaxiRide temp;
        temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
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

    public void fixDoubleBlack(RedBlackTreeNode node) {
        if (node == root)
            // Reached root
            return;

        RedBlackTreeNode sibling = node.sibling(), parent = node.parent;
        if (sibling == null)
            // No sibling, double black pushed up
            fixDoubleBlack(parent);
        else {
            if (sibling.color == Color.RED) {
                // Sibling red
                parent.color = Color.RED;
                sibling.color = Color.BLACK;
                if (sibling.isOnLeft())
                    rotateRight(parent);
                else
                    rotateLeft(parent);
                fixDoubleBlack(node);
            } else {
                // Sibling black
                if (sibling.hasRedChild()) {
                    // At least 1 red children
                    if (sibling.left != null && sibling.left.color == Color.RED) {
                        if (sibling.isOnLeft()) {
                            // left-left
                            sibling.left.color = sibling.color;
                            sibling.color = parent.color;
                            rotateRight(parent);
                        } else {
                            // right-left
                            sibling.left.color = parent.color;
                            rotateRight(sibling);
                            rotateLeft(parent);
                        }
                    } else {
                        if (sibling.isOnLeft()) {
                            // left right
                            sibling.right.color = parent.color;
                            rotateLeft(sibling);
                            rotateRight(parent);
                        } else {
                            // right right
                            sibling.right.color = sibling.color;
                            sibling.color = parent.color;
                            rotateLeft(parent);
                        }
                    }
                    parent.color = Color.BLACK;
                } else {
                    // 2 black children
                    sibling.color = Color.RED;
                    if (parent.color == Color.BLACK)
                        fixDoubleBlack(parent);
                    else
                        parent.color = Color.BLACK;
                }
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

        if (node.value.getRideNumber() >= a && node.value.getRideNumber() <= b) {
            result.add(node);
        }

        if (node.value.getRideNumber() >= a) {
            rangeSearch(node.left, a, b, result);
        }

        if (node.value.getRideNumber() <= b) {
            rangeSearch(node.right, a, b, result);
        }
    }


}
