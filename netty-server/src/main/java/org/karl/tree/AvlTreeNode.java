package org.karl.tree;

/**
 * @Author: KARL ROSE
 * @Date: 2020/4/10 17:46
 **/
public class AvlTreeNode<E extends Comparable<E>> {

    E element;

    AvlTreeNode<E> left;

    AvlTreeNode<E> right;

    int height;

    public AvlTreeNode(E element) {
        this(element, null, null);
    }

    public AvlTreeNode(E element, AvlTreeNode<E> left, AvlTreeNode<E> right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    private int height(AvlTreeNode<E> node) {
        return node == null ? -1 : node.height;
    }


    private AvlTreeNode<E> insert(E element, AvlTreeNode<E> node) {
        if (node == null) {
            return new AvlTreeNode<>(element, null, null);
        }

        int compareResult = element.compareTo(node.element);

        if (compareResult < 0) {
            node.left = insert(element, node.left);
        } else if (compareResult > 0) {
            node.right = insert(element, node.right);
        }

        return balance(node);
    }

    private static final int ALLOWED_BALANCE = 1;

    private AvlTreeNode<E> balance(AvlTreeNode<E> node) {
        if (node == null) {
            return null;
        }
        if (height(node.left) - height(node.right) > ALLOWED_BALANCE) {
            if (height(node.left.left) >= height(left.left.right)) {

            } else {

            }
        } else if (height(node.right) - height(node.left) > ALLOWED_BALANCE) {
            if (height(node.right.right) >= height(node.right.left)) {

            } else {

            }

        }


        return null;
    }

}
