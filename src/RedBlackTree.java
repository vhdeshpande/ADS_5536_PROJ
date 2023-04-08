public class RedBlackTree {
    private RedBlackTreeNode root;

    private boolean ll = false;
    private boolean rr = false;
    private boolean lr = false;
    private boolean rl = false;
    public RedBlackTree(){
        super();
        root = null;
    }

    RedBlackTreeNode rotateLeft(RedBlackTreeNode node)
    {
        RedBlackTreeNode x = node.right;
        RedBlackTreeNode y = x.left;
        x.left = node;
        node.right = y;
        node.parent = x; // parent resetting is also important.
        if(y!=null)
            y.parent = node;
        return x;
    }
    //this function performs right rotation
    RedBlackTreeNode rotateRight(RedBlackTreeNode node)
    {
        RedBlackTreeNode x = node.left;
        RedBlackTreeNode y = x.right;
        x.right = node;
        node.left = y;
        node.parent = x;
        if(y!=null)
            y.parent = node;
        return x;
    }

    RedBlackTreeNode insertHelp(RedBlackTreeNode root, GatorTaxiRide data)
    {
        // f is true when RED-RED conflict is there.
        boolean f=false;

        //recursive calls to insert at proper position according to BST properties.
        if(root == null)
            return(new RedBlackTreeNode(data));
        else if(root.value.getRideNumber() == data.getRideNumber()){
            System.out.println("Duplicate RideNumber");
            return root;
        }
        else if(data.getRideNumber() < root.value.getRideNumber())
        {
            root.left = insertHelp(root.left, data);
            root.left.parent = root;
            if(root!=this.root)
            {
                if(root.color == Color.RED && root.left.color == Color.RED)
                    f = true;
            }
        }
        else
        {
            root.right = insertHelp(root.right,data);
            root.right.parent = root;
            if(root!=this.root)
            {
                if(root.color == Color.RED && root.right.color == Color.RED)
                    f = true;
            }
            // at the same time of insertion, we are also assigning parent nodes
            // also we are checking for RED RED conflicts
        }

        // now lets rotate.
        if(this.ll) // for left rotate.
        {
            root = rotateLeft(root);
            root.color = Color.BLACK;
            root.left.color = Color.RED;
            this.ll = false;
        }
        else if(this.rr) // for right rotate
        {
            root = rotateRight(root);
            root.color = Color.BLACK;
            root.right.color = Color.RED;
            this.rr  = false;
        }
        else if(this.rl)  // for right and then left
        {
            root.right = rotateRight(root.right);
            root.right.parent = root;
            root = rotateLeft(root);
            root.color = Color.BLACK;
            root.left.color = Color.RED;

            this.rl = false;
        }
        else if(this.lr)  // for left and then right.
        {
            root.left = rotateLeft(root.left);
            root.left.parent = root;
            root = rotateRight(root);
            root.color = Color.BLACK;
            root.right.color = Color.RED;
            this.lr = false;
        }
        // when rotation and recolouring is done flags are reset.
        // Now lets take care of RED RED conflict
        if(f)
        {
            if(root.parent.right == root)  // to check which child is the current node of its parent
            {
                if(root.parent.left == null || root.parent.left.color == Color.BLACK)  // case when parent's sibling is black
                {// perform certain rotation and recolouring. This will be done while backtracking. Hence setting up respective flags.
                    if(root.left!= null && root.left.color == Color.RED)
                        this.rl = true;
                    else if(root.right != null && root.right.color == Color.RED)
                        this.ll = true;
                }
                else // case when parent's sibling is red
                {
                    root.parent.left.color = Color.BLACK;
                    root.color = Color.BLACK;
                    if(root.parent!=this.root)
                        root.parent.color = Color.RED;
                }
            }
            else
            {
                if(root.parent.right == null || root.parent.right.color == Color.BLACK)
                {
                    if(root.left != null && root.left.color == Color.RED)
                        this.rr = true;
                    else if(root.right != null && root.right.color == Color.RED)
                        this.lr = true;
                }
                else
                {
                    root.parent.right.color = Color.BLACK;
                    root.color = Color.BLACK;
                    if(root.parent!=this.root)
                        root.parent.color = Color.RED;
                }
            }
            f = false;
        }
        return(root);
    }

    // function to insert data into tree.
    public void insert(GatorTaxiRide data)
    {
        if(this.root==null)
        {
            this.root = new RedBlackTreeNode(data);
            this.root.color = Color.BLACK;
        }
        else
            this.root = insertHelp(this.root,data);
    }

    void inorderTraversalHelper(RedBlackTreeNode node)
    {
        if(node!=null)
        {
            inorderTraversalHelper(node.left);
            System.out.printf("(%d %d %d)", node.value.getRideNumber(), node.value.getRideCost(), node.value.getTripDuration());
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

    public void cancelRide(Integer rideNumber)
    {
        if(this.root == null)
        {
            return;
        }
        else
        {
            RedBlackTreeNode nodeToDelete = search(rideNumber);
            if(nodeToDelete.value.getRideNumber() != rideNumber){
                return;
            }
            else
            {
                deleteNode(nodeToDelete);
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


}
