package org.karl.sh.core.utils;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author KARL ROSE
 * @date 2021/1/26 11:55
 **/
public class BloomFilterHelper<T> {

    private final int numHashFunctions;

    private final int bitSize;

    private final Funnel<T> funnel;


    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    /**
     * @param value
     * @author KARL.ROSE
     * @date 2021/1/26 下午3:56
     **/
    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];
        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }
        return offset;
    }

    /**
     * 计算bit数组的长度
     *
     * @param n 记录数
     * @param p false positive率
     * @author KARL.ROSE
     * @date 2021/1/26 下午3:40
     **/
    private static int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    public static void main(String[] args) {
        /*BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>(new Funnel<String>() {
            @Override
            public void funnel(String from, PrimitiveSink into) {
                into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8);
            }
        }, 10000, 0.01);*/
        BloomFilterHelper<CharSequence> myBloomFilterHelper = new BloomFilterHelper<>(Funnels.stringFunnel(StandardCharsets.UTF_8), 10000, 0.01);
        System.out.println(Arrays.toString(myBloomFilterHelper.murmurHashOffset("user_bloom")));
    }

    /**
     * 计算hash方法执行次数
     *
     * @param n 记录数
     * @param m bit数组长度
     * @author KARL.ROSE
     * @date 2021/1/26 下午3:46
     **/
    private static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }


}
