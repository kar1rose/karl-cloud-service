package org.karl;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author KARL ROSE
 * @date 2021/2/3 15:52
 **/
public class Demo {

    private static List<KeyPath> list;

    static {
        KeyPath end = new KeyPath("全桌组装", 1, null);
        KeyPath var0 = new KeyPath("油漆", 0.5, end);
        KeyPath var1 = new KeyPath("抽屉油漆", 2, end);
        KeyPath var2 = new KeyPath("组装", 0.5, var0);
        KeyPath var3 = new KeyPath("抛光", 0.5, var2);
        KeyPath var4 = new KeyPath("打磨", 1, var2);
        KeyPath var7 = new KeyPath("抽屉组装", 1, var1);

        KeyPath var5 = new KeyPath("桌面开料", 0.5, var3);
        KeyPath var6 = new KeyPath("桌腿开料", 0.5, var4);
        KeyPath var8 = new KeyPath("抽屉板开料", 1, var7);

        list = new ArrayList<>();
        list.add(var5);
        list.add(var6);
        list.add(var8);
    }

    //DFS
    public static void main(String[] args) {
        Stack<KeyPath> stack = new Stack<>();
        double maxCost = 0;
        List<String> pathList = new ArrayList<>();
        for (KeyPath keyPath : list) {
            if (stack.contains(keyPath)) {
                //出现环
                continue;
            }
            double cost = 0;
            while (keyPath != null) {
                stack.push(keyPath);
                cost += keyPath.time;
                keyPath = keyPath.next;
            }
            if (maxCost < cost) {
                //出现更大的cost
                maxCost = cost;
                pathList.clear();
                while (!stack.isEmpty()) {
                    pathList.add(stack.pop().ele);
                }
            }
        }
        for (int i = pathList.size() - 1; i >= 0; --i) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.print("Cost:" + Math.round(maxCost) + " Hours");


    }

    static class KeyPath {

        String ele;
        double time;
        KeyPath next;

        public KeyPath(String ele, double time, KeyPath next) {
            this.ele = ele;
            this.time = time;
            this.next = next;
        }
    }
}
