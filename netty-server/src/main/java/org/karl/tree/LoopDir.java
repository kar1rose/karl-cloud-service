package org.karl.tree;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/4/9 14:28
 **/
@Slf4j(topic = "遍历目录")
public class LoopDir {

    public static void main(String[] args) {
        Integer level = 0;
        File file = new File("/Users/arvato/go_projects");
        loop(file, level);
    }

    /**
     * @param file
     * @param level
     * @Author KARL.ROSE
     * @Date 2020/4/22 10:54 上午
     **/
    private static void loop(File file, Integer level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("-");
        }
        String s = new String(sb);
        if (file.isDirectory()) {
            log.info(s + file.getName() + "/");
            File[] list = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    if (!f.isDirectory()) {
                        log.info(s + f.getName());
                    } else {
                        level++;
                        loop(f, level);
                    }
                }
            }
        } else {
            log.info(s + file.getName());
        }

    }
}
