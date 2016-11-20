public class Main {

    public static BSTree generateTree1() {
        int[] keyList = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        BSTree tree = new BSTree();
        for (int i = 0; i < keyList.length; i++)
            tree.insert(keyList[i]);
        return tree;
    }

    public static void main(String[] args) {

        /*BSTree tree;
        tree = generateTree1();
        tree.printTree();
        NodeList list = tree.split(6);
        (new BSTree(list.r1)).printTree();
        (new BSTree(list.r2)).printTree();*/
        testzigzag();

    }

    static void testzigzag(){
        SplayTree tree1 = new SplayTree();
        int[] keyList = {5, 7, 2, 3, 1, 6, 8};
        for (int i = 0; i < keyList.length; i++) {
            tree1.insert(keyList[i]);
            Node x = tree1.findWithoutSplaying(5);
            /*if(x != null && x.parent!=null){
                System.out.println("5 parent " + x.parent.key);
                System.out.println("after add "+ keyList[i]);
                tree1.printTree();
            }*/
        }
        tree1.printTree();
        System.out.println("ZigZig Node (1)");
        tree1.zigzig(tree1.findWithoutSplaying(1));
        tree1.printTree();
        System.out.println("ZigZag Node (5)");
        tree1.zigzag(tree1.findWithoutSplaying(5));
        tree1.printTree();
        System.out.println("ZigZag Node (5)");
        tree1.zigzag(tree1.findWithoutSplaying(5));
        tree1.printTree();
        System.out.println("ZigZag Node (7)");
        tree1.zigzag(tree1.findWithoutSplaying(7));
        tree1.printTree();
        System.out.println("ZigZag Node (2)");
        tree1.zigzag(tree1.findWithoutSplaying(2));
        tree1.printTree();
        System.out.println("ZigZag Node (3)");
        tree1.zigzag(tree1.findWithoutSplaying(3));
        tree1.printTree();
        System.out.println("ZigZig Node (7)");
        tree1.zigzig(tree1.findWithoutSplaying(7));
        tree1.printTree();
    }
}
