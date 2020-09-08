package org.karl.sh.core.utils;

/**
 * 雪花算法
 * 分布式全局唯一ID生成器
 *
 * @author KARL.ROSE
 * @date 2020/5/19 6:34 下午
 **/
public class SnowFlake {

    /**
     * 序列号占用的位数
     **/
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     **/
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数
     **/
    private final static long DATA_CENTER_BIT = 5;
    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;


    /**
     * 起始的时间戳
     */
    private static final long START_STAMP = 1577808000000L;
    /**
     * 数据中心
     **/
    private final long dataCenterId;
    /**
     * 机器标识
     **/
    private final long machineId;
    /**
     * 序列号
     **/
    private long sequence = 0L;
    /**
     * 上一次时间戳
     **/
    private long lastStamp = -1L;

    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return ID
     */
    public synchronized long nextId() {
        long currStamp = getTimestamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getTimestamp();
        while (mill <= lastStamp) {
            mill = getTimestamp();
        }
        return mill;
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(snowFlake.nextId());
        }
    }
}
