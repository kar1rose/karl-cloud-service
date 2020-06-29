package org.karl.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * @author KARL ROSE
 * @date 2020/6/10 18:42
 **/

public class Participle {

    private static final Logger log = LogManager.getLogger(Participle.class);

    public static void main(String[] args) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        /*String[] sentences = new String[]{"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
        for (String sentence : sentences) {
            log.info(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
        }*/

        String msg = "雷猴回归人间。工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作";
        List<SegToken> segTokens = segmenter.process(msg, JiebaSegmenter.SegMode.SEARCH);
        segTokens.forEach(segToken -> {
            log.info(segToken.word);
        });
    }
}
