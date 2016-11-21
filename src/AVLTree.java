public class AVLTree extends BTreePrinter {

    Node root;

    public AVLTree() {

    }

    public AVLTree(Node root) {
        this.root = root;
        this.root.parent = null; // To make sure the root has no parent
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Empty tree!!!");
        } else {
            super.printTree(root);
        }
    }

    public Node find(int search_key) {
        return find(root, search_key);
    }

    public static Node find(Node node, int search_key) {
        if (search_key == node.key) {
            return node;
        } else if ((search_key < node.key) && (node.left != null)) {
            return find(node.left, search_key);
        } else if ((search_key > node.key) && (node.right != null)) {
            return find(node.right, search_key);
        } else {
            return null;
        }
    }

    public Node findMin() {
        return findMin(root);
    }

    public static Node findMin(Node node) {
        if (node==null || node.left == null) {
            return node;
        } else {
            return findMin(node.left);
        }
    }

    public Node findMax() {
        return findMax(root);
    }

    public static Node findMax(Node node) {
        if (node==null || node.right == null) {
            return node;
        } else {
            return findMax(node.right);
        }
    }
    
    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
        } else {
            insert(this, root, key);

        }
    }

    // Fix this function to have the rebalancing feature
    // There should be rebalance function calling somewhere in the code
    public static void insert(AVLTree tree, Node node, int key) {
        if (key == node.key) {
            System.out.println("Duplicated key:" + key);
        } else if (key < node.key) {//Go left
            if (node.left == null) {
                node.left = new Node(key);
                node.left.parent = node;


            } else {
                insert(tree, node.left, key);
            }
        } else{ // Go right
            if (node.right == null) {
                node.right = new Node(key);
                node.right.parent = node;


            } else {
                insert(tree, node.right, key);
            }
        }
        rebalance(tree,node);

    }
    
    // Fix this
    // If you do not understant my code, feel free to implement your own code
    public static void rebalance(AVLTree tree, Node node){
        if (node.isImbalance()){ // when it is not balance
            if (Node.height(node)>2){
                if (Node.height(node.left) > Node.height(node.right)){ //if left higher than right

                    rebalance(tree,node.left);

                }else{                                                  //otherwise

                    rebalance(tree,node.right);
                }
            }
            if (Node.height(node.left) > Node.height(node.right)) { // Left heavy?
                if (Node.height(node.left.left) > Node.height(node.left.right)) { // Outer?
                    System.out.println("Perform singleRotationFromLeft (Node " + node.key +")");
                    tree.singleRotateFromLeft(node);

                }else{ // Inner?
                    System.out.println("Perform doubleRotateFromLeft (Node " + node.key +")");
                    // Do something (just add one line here)
                    tree.doubleRotateFromLeft(node);
                }
            }else{ // Right heavy?
                if (Node.height(node.right.right) > Node.height(node.right.left)) { //Outer?
                    System.out.println("Perform singleRotateFromRight (Node " + node.key +")");
                    // Do something (just add one line here)
                    tree.singleRotateFromRight(node);
                } else { // Inner?
                    System.out.println("Perform doubleRotateFromRight (Node " + node.key +")");
                    // Do something (just add one line here)
                    tree.doubleRotateFromRight(node);
                }
            }
        }
    }

    public void singleRotateFromLeft(Node y) {
        if (y != null) {
            Node x = y.left;
            Node b = x.right;
            y.left = b;

            if(b != null){
                b.parent = y;
            }
            x.right = y;
            x.parent = y.parent;
            y.parent = x;
            if (x.parent == null) {
                root = x;
            }else{
                if (x.parent.key < x.key) {
                    x.parent.right = x;
                } else {
                    x.parent.left = x;
                }
            }



        }
    }

    public void singleRotateFromRight(Node y) {
        if (y != null) {
            Node x = y.right;
            Node b = x.left;

            y.right = b;
            if(b != null){
                b.parent = y;
            }
            x.left = y;
            x.parent = y.parent;
            y.parent = x;
            if (x.parent == null) {
                root = x;
            }else{
                if (x.parent.key < x.key) {
                    x.parent.right = x;
                } else {
                    x.parent.left = x;
                }
            }


        }
    }

    public void doubleRotateFromLeft(Node y) {
        singleRotateFromRight(y.left);
        singleRotateFromLeft(y);
    }

    public void doubleRotateFromRight(Node y) {
        singleRotateFromLeft(y.right);
        singleRotateFromRight(y);
    }


    // Fix this function to have the rebalancing feature
    // There should be rebalance function calling somewhere in the code
    // This function will delete root or call the recursive delete function
    public void delete(int key) {
        if (root == null) {
            System.out.println("Empty Tree!!!");
        } else if (root.key == key) { // Delete root node
            if ((root.left == null) && (root.right == null)) { // Case 1 (leaf node)
                root = null;
            } else if ((root.left != null) && (root.right != null)) { // Case 3 (full node)
                Node minRightSubTree = findMin(root.right);
                Node temp = new Node(minRightSubTree.key);
                replace(root, temp);
                root = temp;
                // recursively delete the node
                delete(this, root.right, minRightSubTree.key);
            } else if (root.left != null) { // Case 2 (single child, left child)
                root = root.left; // promote the left child to the root
            } else { // Case 2 (single child, right child)
                root = root.right; // promote the right child to the root
            }




        } else { // Delete non-root node
            delete(this, root, key);
        }
        rebalance(this,root); // rebalance after delete root

    }

    // Fix this function to have the rebalancing feature
    // There should be rebalance function calling somewhere in the code
    // This function will delete non-root nodes
    public static void delete(AVLTree tree, Node node, int key) {
        if (node==null)
        {
            System.out.println("Key not found!!!");
        }else if (node.key > key){ // Go left
            delete(tree, node.left, key);
        }else if (node.key < key){ // Go right
            delete(tree, node.right, key);
        }else if (node.key == key){ // Node to be deleted is found
            if ((node.left == null) && (node.right == null)) { // Case 1(leaf node)
                if (node.key < node.parent.key) {
                    node.parent.left = null; // disown the left child
                } else {
                    node.parent.right = null; // disown the right child
                }
            } else if ((node.left != null) && (node.right != null)) { // Case 3 (full nodes)
                Node minRightSubTree = findMin(node.right);
                Node temp = new Node(minRightSubTree.key);
                replace(node, temp);
                // recursively delete the node
                delete(tree, node.right, minRightSubTree.key);
            } else {// Case 2 (single child)
                Node childNode;
                if (node.left != null) {  // only the left child
                    childNode = node.left;
                } else { // only the right child
                    childNode = node.right;
                }
                childNode.parent = node.parent;
                if (node.parent.key > node.key) {
                    node.parent.left = childNode;
                } else {
                    node.parent.right = childNode;
                }
            }
        }
        rebalance(tree,node); //after done delete , rebalance it

    }
    
    // Replace node1 with a new node2
    // node2 must be created using "new Node(key)" before calling this function
    // This function is optional, you do not have to use it
    public static void replace(Node node1, Node node2) {
        if ((node1.left != null) && (node1.left != node2)) {
            node2.left = node1.left;
            node1.left.parent = node2;
        }
        if ((node1.right != null) && (node1.right != node2)) {
            node2.right = node1.right;
            node1.right.parent = node2;
        }
        if ((node1.parent != null) && (node1.parent != node2)) {
            node2.parent = node1.parent;
            if (node1.parent.key > node1.key) {
                node1.parent.left = node2;
            } else {
                node1.parent.right = node2;
            }
        }
    }

    public static boolean isMergeable(Node r1, Node r2) { // same with BSTree
        if (r1 == null || r2 == null) {
            return true;
        }

        if (r1.right == null && r2.left == null) {
            if (r1.key < r2.key) {
                return true;
            } else {
                return false;
            }
        } else if (r1.right == null && r2.left != null) {
            return isMergeable(r1, r2.left);
        } else {
            return isMergeable(r1.right, r2);
        }
    }

    public static Node mergeWithRoot(Node r1, Node r2, Node t){
        if (isMergeable(r1,r2)){ // same with BSTree
            t.left = r1;
            t.right = r2;
            if (r1 != null) r1.parent = t;
            if (r2 != null) r2.parent = t;

            rebalance(new AVLTree(t),t); //rebalance after merge
            return t;

        }else{

            System.out.println("All nodes in T1 must be smaller than all nodes from T2");
            return null;
        }
    }
          
    public void merge(AVLTree tree2){
        if (isMergeable(this.root, tree2.root)){ //when it can merge
           Node t = new Node(findMax(root).key);//new node with max value of left tree
            delete(findMax(root).key);  //delete node that have same key with t
            t =mergeWithRoot(root,tree2.root,t); //merge and store new root in t
            root = t;

        }else{
            System.out.println("All nodes in T1 must be smaller than all nodes from T2");
        }
        
    }
    
    public NodeList split(int key){ //Same with BSTree
        return split(this.root, key);
    }
    public static NodeList split(Node r, int key){
        NodeList list = new NodeList();
        if (r == null) {
            return list;
        } else if (key < r.key) {
            list = split(r.left, key);
            Node r3 = mergeWithRoot(list.r2, r.right, r);
            list.r2 = r3;
            return list;
        } else { // key>=root.key
            list = split(r.right, key);
            Node r4 = mergeWithRoot(r.left, list.r1, r);
            list.r1 = r4;
            return list;
        }
    }
}
