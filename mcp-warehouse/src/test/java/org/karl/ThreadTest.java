package org.karl;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * @author KARL ROSE
 * @date 2020/12/16 11:02
 **/
public class ThreadTest {

    private static char letter = 'A' - 1;
    private static int num = 0;

    private static Thread thread1 = null;
    private static Thread thread2 = null;


    public static void main(String[] args) throws InterruptedException {
        thread1 = new Letter();
        thread2 = new Num();
        thread1.start();
        thread2.start();
    }

    static class Letter extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            int index = 0;
            while (index < 26) {
                index++;
                System.out.print(++letter);
                // 打印完让线程2执行
                LockSupport.unpark(thread2);
                // 线程1等待
                LockSupport.park(thread1);
            }
        }
    }

    static class Num extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            int index = 0;
            while (index < 26) {
                index++;
                // 上来线程2先等待
                LockSupport.park(thread2);
                System.out.print(++num);
                // 输出完唤醒线程1，让线程1执行
                LockSupport.unpark(thread1);
            }
        }
    }

    public boolean wordPattern(String pattern, String s) {
        String[] strs = s.split(" ");
        char[] charArray = pattern.toCharArray();
        HashMap<Character, String> map1 = new HashMap<>();
        HashMap<String, Character> map2 = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char c = charArray[i];
            if (!map1.containsKey(c) && !map1.containsValue(strs[i])) {
                map1.put(c, strs[i]);
                map2.put(strs[i], c);
            } else if (!map1.get(c).equals(strs[i]) ||map2.get(strs[i]).equals(c)) {
                return false;
            }
        }
        return true;
    }
}
