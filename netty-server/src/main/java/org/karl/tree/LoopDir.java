package org.karl.tree;

import org.karl.base.async.CallableJoinDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author KARL ROSE
 * @date 2020/4/9 14:28
 **/
public class LoopDir {


    private static final Logger logger = LoggerFactory.getLogger(LoopDir.class);

    public static void main(String[] args) {
        Integer level = 0;
        File file = new File("/Users/arvato/go_projects");
        loop(file, level);
    }

    /**
     * @param file
     * @param level
     * @author KARL.ROSE
     * @Date 2020/4/22 10:54 上午
     **/
    private static void loop(File file, Integer level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("-");
        }
        String s = new String(sb);
        if (file.isDirectory()) {
            logger.info(s + file.getName() + "/");
            File[] list = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    if (!f.isDirectory()) {
                        logger.info(s + f.getName());
                    } else {
                        level++;
                        loop(f, level);
                    }
                }
            }
        } else {
            logger.info(s + file.getName());
        }

    }
}
