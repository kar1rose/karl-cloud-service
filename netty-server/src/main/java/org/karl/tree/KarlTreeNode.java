package org.karl.tree;

/**
 * @author KARL ROSE
 * @date 2020/4/9 14:45
 **/
public class KarlTreeNode<E> {

    E node;
    KarlTreeNode<E> left;
    KarlTreeNode<E> right;

    public KarlTreeNode(E node) {
        this(node, null, null);
    }

    public KarlTreeNode(E node, KarlTreeNode<E> left, KarlTreeNode<E> right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }
}
