package org.karl.tree;

/**
 * @Author: KARL ROSE
 * @Date: 2020/4/9 15:09
 **/
public class BinarySearchTree<E extends Comparable<E>> {

    /**
     * 根结点
     **/
    private BinaryNode<E> root;

    /**
     * 节点构造方法
     **/
    private static class BinaryNode<E> {
        E node;
        KarlTreeNode<E> left;
        KarlTreeNode<E> right;

        BinaryNode(E node) {
            this(node, null, null);
        }

        BinaryNode(E node, KarlTreeNode<E> left, KarlTreeNode<E> right) {
            this.node = node;
            this.left = left;
            this.right = right;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
