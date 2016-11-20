public class Node{
    Node left;
    Node right;
    Node parent;
    int key;
    
    public Node(int data){
        this.key = data;
    }

    // To simplify the problem, Please use 'height(node)' to evaluate height of the node
    public static int height(Node node){
        if (node == null)
            return -1;
        else
            return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isImbalance(){
        if(Math.abs(height(left) - height(right)) >= 2){
            return true;
        }else {
            return false;
        }
    }
}
