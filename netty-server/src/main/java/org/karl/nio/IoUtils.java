package org.karl.nio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author: KARL ROSE
 * @Date: 2020/4/10 10:30
 **/
public class IoUtils {

    public static void main(String[] args) {

    }

    public static void fileCopy(String src, String target) throws IOException {
        FileUtils.copyDirectory(new File(src), new File(target));
    }

}
