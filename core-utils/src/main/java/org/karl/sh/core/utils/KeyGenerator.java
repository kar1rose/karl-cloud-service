package org.karl.sh.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 主键生成器 适合单体应用并发的ID生成
 * 分布式高并发情况下可能会产生相同的ID
 *
 * @author KARL.ROSE
 * @date 2020/4/23 16:41
 **/
@Slf4j
public class KeyGenerator {

    private static long tmpID = 0;
    private static final long INCREASE_STEP = 1;
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");


    public synchronized static Long nextId() {
        //当前：（年、月、日、时、分、秒、毫秒）
        long timeCount;
//        if (LOCK.tryLock(LOCK_TIME, TimeUnit.SECONDS)) {
        timeCount = Long.parseLong(DATE_FORMATTER.format(LocalDateTime.now()));
//            try {
        if (tmpID < timeCount) {
            tmpID = timeCount;
        } else {
            tmpID += INCREASE_STEP;
            timeCount = tmpID;
        }
        return timeCount;
//            } finally {
//                LOCK.unlock();
//            }
//        } else {
//            log.error("lock failed");
//            return nextId();
//
//        }
    }
}
