package org.karl.sh.boot;

import java.util.HashMap;

/**
 * @author KARL ROSE
 * @date 2020/7/24 17:51
 **/
public class MapTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("重地", "rose");
        map.put("通话", "rose1");
        System.out.println(map);

    }


    static class Node {
        final String key;
        Node next;

        public Node(String key, Node next) {
            this.key = key;
            this.next = next;
        }
    }
}
