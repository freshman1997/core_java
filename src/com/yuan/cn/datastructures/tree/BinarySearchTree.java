package com.yuan.cn.datastructures.tree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Stack;

/**
 * floor 、 ceil、 rank（排名是第几）、select（排在第几的元素是什么） 待实现
 * 可以给node加一个size维护，然后可以容易实现 depth 深度也可以
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable> {

    private class Node {
        E e;
        Node left, right;

        Node(E e) {
            this.e = e;
            left = right = null;
        }
    }

    private Node root;
    private int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E e) {
//       if(root == null) {
//           root = new Node(e);
//           size++;
//       }else {
//           add(root, e);
//       }
        root = add(root, e);
    }

    // 向以node为根的二分搜索树中插入元素E，递归算法
    private Node add(Node node, E e) {
//        // 也就是比较相等，只保留一个元素
//        if(node.e.equals(e))
//            return;
//        // 如果小于并且左边为null，则插入为左叶子节点
//        else if(e.compareTo(node.e) < 0 && node.left == null)
//        {
//            node.left = new Node(e);
//            size++;
//            return;
//        }else if(e.compareTo(node.e) > 0 && node.right == null){
//            node.right = new Node(e);
//            size++;
//            return;
//        }
        if (node == null) {
            size++;
            return new Node(e);
        }
        // 如果当前元素小于父节点的元素的值并且不为null，则插入到左叶子节点
        // 如果遇到相等的元素就相当于什么都不做
        // 如果子节点为空的话就执行上面的逻辑：创建一个新的节点并插入元素连接父节点
        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }
        return node;
    }

    // 查找是否存在这个元素
    public boolean contains(E e) {
        // 从根节点开始搜索
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {
        // 找到尾节点都没有找到
        if (node == null)
            return false;
        // 找到了元素
        if (e.compareTo(node.e) == 0) {
            return true;
            // 找左边叶子节点
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    // 前序遍历
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 前序遍历（DLR），是二叉树遍历的一种，也叫做先根遍历、先序遍历、前序周游，可记做根左右。前序遍历首先访问根结点然后遍历左子树，最后遍历右子树。
     * @param node
     */
    private void preOrder(Node node) {
        // 递归到了中止条件，也就是到达了尾节点，不论左还是右
        if (node == null)
            return;

        System.out.println(node.e);

        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(){
        inOrder(root);
    }

    /**
     * 中序遍历（LDR）是二叉树遍历的一种，也叫做中根遍历、中序周游。在二叉树中，中序遍历首先遍历左子树，然后访问根结点，最后遍历右子树。
     * @param node
     */
    private void inOrder(Node node){
        if(node == null)
            return;

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }
    public void postOrder(){
        postOrder(root);
    }

    /**
     * 后序遍历（LRD）是二叉树遍历的一种，也叫做后根遍历、后序周游，可记做左右根。后序遍历有递归算法和非递归算法两种。
     * 在二叉树中，先左后右再根，即首先遍历左子树，然后遍历右子树，最后访问根结点。
     * @param node 节点
     */
    private void postOrder(Node node){
        if(node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    public void inOrderNR(){
        Stack<Node> stack = new Stack<>();
        Node current = root;
        while(current != null || !stack.empty())
        {
            while (current != null)
            {
                stack.push(current);
                current = current.left;
            }
            if(!stack.empty())
            {
                current = stack.pop();
                System.out.println(current.e);
                // 到右边节点，然后又回到前面的循环里面
                current = current.right;
            }
        }
    }

    public void postOrderNR(){
        Stack<Node> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        int i = 1;
        Node current = root;
        int count = 0;
        while(current != null || !stack1.empty())
        {
            while (current != null)
            {
                stack1.push(current);
                stack2.push(0);
                count++;
                current = current.left;
            }


            while(!stack1.empty() && stack2.peek() == i)
            {
                stack2.pop();
                System.out.println(stack1.pop().e);
            }

            if(!stack1.empty())
            {
                // 遇到一次null则进行 置1
                stack2.pop();
                stack2.push(1);

                current = stack1.peek();
                current = current.right;

            }
        }
        System.out.println(" ====> "+count);
    }

    public void remove(E e){
        root = remove(root, e);
    }

    private Node remove(Node node, E e) {
        if(node == null)
            return null;

        if(e.compareTo(node.e) < 0){
            node.left = remove(node.left, e);
            return node;
        }else if(e.compareTo(node.e) > 0){
            node.right = remove(node.right, e);
            return node;
        } else{
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 左右都有的节点要删除，找到比待删除节点打的最小节点，即待删除节点右子树的最小节点
            // 用这个节点代替待删除的节点的位置
            // 这是后继写法   还有一个前驱写法，也就是左子树的最近最大的节点来替代要删除的节点
            Node successor = getMin(node.right);
            successor.right = removeMin(node.right);

            successor.left = node.left;

            node.right = node.left = null;
            return successor;
        }
    }

    /**
     * 层序遍历
     */
    public void preOrderNR(){
        Stack<Node> stack = new Stack<>();
        Node current = root;
        while (! stack.isEmpty()){
            while (current != null){
                stack.push(current);
                current = current.left;
            }
            while( current != null){
                current = current.right;
            }
        }
    }

    public void levelOrder(){
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            Node current = queue.remove();
            System.out.println(current.e);
            if(current.left != null)
                queue.add(current.left);
            if(current.right != null)
                queue.add(current.left);
        }
    }

    public E getMin(){
        if(size == 0)
            throw new IllegalArgumentException("Tree is null.");
        return getMin(root).e;
    }
    private Node getMin(Node node){
        if(node.left == null)
            return node;
        return getMin(node.left);
    }

    public E removeMin(){
        E e = getMin();

        root = removeMin(root);
        System.out.println(root.e);

        return e;
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node) {
        if(node.left == null)
        {
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }
        // 返回的右子节点作为头结点
        node.left = removeMin(node.left);
        System.out.println(node.e+"=");
        // 已经删除完成之后的新的树的根节点 返回
        return node;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }
    private void generateBSTString(Node node, int depth, StringBuilder res) {
        if(node == null)
        {
            res.append(generateDepthString(depth)).append("null\n");
            return;
        }

        res.append(generateDepthString(depth)).append(node.e).append("\n");
        generateBSTString(node.left, depth+1, res);
        generateBSTString(node.right, depth+1, res);
    }
    private String generateDepthString(int depth) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i< depth; i++)
            builder.append("--");
        return builder.toString();
    }

    @Test
    public void test() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] numbers = {5, 3, 6, 8, 9, 10, 4, 7,2,1};
        for(int num : numbers)
            tree.add(num);
//
//        tree.preOrder();
//        System.out.println();
//
//        tree.inOrder();
//        System.out.println();
//        tree.postOrder();
//        System.out.println();
//        tree.postOrderNR();
        tree.removeMin();
        System.out.println(tree);
    }

}
