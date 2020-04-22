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

    /**
     * 平衡树
     **/
    private AvlTreeNode<E> balance(AvlTreeNode<E> node) {
        if (node == null) {
            return null;
        }
        if (height(node.left) - height(node.right) > ALLOWED_BALANCE) {
            if (height(node.left.left) >= height(left.left.right)) {
                node = rotateWithLeftChild(node);
            } else {
                node = doubleWithLeftChild(node);
            }
        } else if (height(node.right) - height(node.left) > ALLOWED_BALANCE) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = rotateWithRightChild(node);
            } else {
                node = doubleWithRightChild(node);
            }

        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }


    private AvlTreeNode<E> rotateWithLeftChild(AvlTreeNode<E> k2) {
        AvlTreeNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k2)) + 1;
        return k1;
    }

    private AvlTreeNode<E> doubleWithLeftChild(AvlTreeNode<E> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }


    private AvlTreeNode<E> rotateWithRightChild(AvlTreeNode<E> k2) {
        AvlTreeNode<E> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.right), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.right), height(k2)) + 1;
        return k1;

    }

    private AvlTreeNode<E> doubleWithRightChild(AvlTreeNode<E> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }


}
