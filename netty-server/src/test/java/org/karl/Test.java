package org.karl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * @author KARL ROSE
 * @date 2020/4/7 17:41
 **/
@Slf4j
public class Test {

    private static final Integer MAX = 4000000;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            log.info("{}", new Random().nextInt(3));
        }
    }

    private void demo() {
        List<Integer> L = new ArrayList<>();
        L.add(MAX / 100000);
        L.add(MAX / 10000);
        L.add(MAX / 1000);

        List<Integer> P = new ArrayList<>();
        for (int i = 1; i <= MAX; i++) {
            P.add(i);
        }
        long start = System.currentTimeMillis();
        printP(L, P);
        long end = System.currentTimeMillis();
        log.info("used:>>>{}", end - start);
    }


    public static void printP(List<Integer> L, List<Integer> P) {
        /*Iterator<Integer> iterator = P.iterator();
        while (iterator.hasNext()) {

        }*/
        for (Integer integer : L) {
            log.info("P[{}]={}", integer, P.get(integer));
        }
    }

    public void test() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(0, i);
        }

        ListIterator<Integer> iterator = list.listIterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
            if (next % 2 == 0) {
                iterator.set(next + 1);
            }
        }
        System.out.println(JSON.toJSONString(list));
    }
}
