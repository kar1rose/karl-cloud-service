package org.karl.sh.boot.model;

/**
 * @author KARL ROSE
 * @date 2020/7/24 19:14
 **/
public class Demo {

    public void test() {
        Node p = new Node(1, null);
        Node e;
        if ((e = p.next) == null) {
            p.next = new Node(2, null);
        }
        System.out.println();
    }


    static class Node {
        Integer key;
        Node next;

        public Node(Integer key, Node next) {
            this.key = key;
            this.next = next;
        }
    }
}
