

public class SplayTree extends BTreePrinter{
    Node root;
    
    public SplayTree(){
        
    }
    
    public SplayTree(Node root){
        
        if (root.parent!=null){ // To make sure the root has no parent
            if (root.key < root.parent.key){
                root.parent.left = null;
            }else{
                root.parent.right = null;
            }
            root.parent = null;
        }
        this.root = root;
    }
    
    public void printTree(){
        super.printTree(root);
    }
    
    public Node find(int search_key) {
        Node f = find(root,search_key);
        if(f != null){
            splay(f); //when found node ,splay it to root

        }
        return f; // and return
    }

    // This function is already complete, no need to modify
    public Node findWithoutSplaying(int search_key) {
        return find(root, search_key);
    }
    
    // This function is already complete, no need to modify
    private static Node find(Node node, int search_key) {
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
    
    // This function is already complete, no need to modify
    private Node findMin() {
        return findMin(root);
    }

    // This function is already complete, no need to modify
    private static Node findMin(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return findMin(node.left);
        }
    }
    
    // This function is already complete, no need to modify
    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
        } else {
            insert(this, root, key);
        }
    }

    // Fix this function to have splaying feature
    private static void insert(SplayTree tree, Node node, int key) {
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
        tree.splay(tree.findWithoutSplaying(key)); //when insert complete , splay that key node to root
    }
    
    
    public void delete(int key) {
        if(find(key) == null){
            System.out.println("No delete ,key not found.");
        }else{
            Node node = find(key); //1


            SplayTree newT = new SplayTree(node.right);//2
            newT.find(newT.findMin().key);//3
            newT.root.left = node.left;//4

            root = newT.root;//5
        }


        // To delete a key in a splay tree, do the following steps
        // 1. splay node with the key to the root of tree1
        // 2. create another tree using the node of the right-subtree (tree2)
        // 3. Find min of the right-subtree and splay to the root
        // 4. Take left-subtree of the tree1 and hang as the left subtree of the tree2
        // 5. Reassign a new root (root of the tree2)
    }
    
    // Use this function to call zigzig or zigzag
    public void splay(Node node){
        if (node!=null && node != root){
            if (node.parent == root){//if can rotate one time.

                zig(node);
            }else{
                if (node.key < node.parent.key){
                    if (node.parent.key < node.parent.parent.key){
                        // Left outer case

                        zigzig(node);
                    }else{
                        // Left inner case

                        zigzag(node);
                    }
                }else{
                    if (node.parent.key > node.parent.parent.key){
                        // Right outer case

                        zigzig(node);
                    }else{//Right inner case

                        zigzag(node);
                    }
                }

                splay(node); //recursive part
            }
        }
    }



    public void zigzig(Node node){ // Move two nodes up along the Outer path
        zig(node.parent);
        zig(node);
    }
    

    public void zigzag(Node node){ // Move two nodes up along the Inner path
        zig(node);
        zig(node);
    }
    
    // This function should be called by zigzig or zigzag
    public void zig(Node node){// Move up one step
        if (node.parent == null){
            System.out.println("Cannot perform Zig operation on the root node");
        }else if (node.parent == root){ // If the node is a child of the root
            if (node.key<node.parent.key){// Zig from left
               singleRotateFromLeft(node.parent); // same with BSTree but zig input is child


            }else{ // Zig from right
                singleRotateFromRight(node.parent);  // same with BSTree but zig input is child


            }
        }else{// if the node is not a child of the root
            if (node.key<node.parent.key){// Zig from left
                singleRotateFromLeft(node.parent);  // same with BSTree but zig input is child

            }else{
                singleRotateFromRight(node.parent);  // same with BSTree but zig input is child

            }
        }
    }
    //------------------------borrow BSTree------
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
//------------------------borrow BSTree------
}
