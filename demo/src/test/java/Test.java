/**
 * Created by KARL_ROSE on 2020/3/8 13:38
 */

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Test
 * @Author ROSE
 * @Date 2020/3/8 13:38
 * @Description
 **/
@Slf4j
public class Test {

    private static final int m = 2;

    public static void main(String[] args) {
//        log.info(3 % 5 + "");
        joseph(10);
//        log.info(String.valueOf(josephusByRec(10, 3)));
    }

    public static void joseph(int n) {
        //组装序列
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        int k = 0;
        while (list.size() > 1) {
            k += m;
            // 第m人的索引位置
            k = k % list.size() - 1;
            log.info("K=" + k);
            if (k < 0) {
//                log.info(list.get(list.size() - 1) + "");
                list.remove(list.size() - 1);
                k = 0;
            } else {
//                log.info(list.get(k) + "");
                list.remove(k);
            }
            log.info(JSON.toJSONString(list));
        }
    }

    public static int josephusByRec(int n, int m) {
        if (n == 1) {  //出口
            return 0;
        }
        return (josephusByRec(n - 1, m) + m) % n;
    }


}
