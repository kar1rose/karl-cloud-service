package org.karl;
/**
 * Created by KARL_ROSE on 2020/4/9 23:13
 */

import lombok.extern.slf4j.Slf4j;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.NoSuchFileException;

/**
 * @ClassName CustomBuffer
 * @Author ROSE
 * @Date 2020/4/9 23:13
 * @Description
 **/
@Slf4j
public class CustomBuffer {
    public static void main(String[] args) throws IOException {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel channelIn = null;
        FileChannel channelOut = null;

        try {
            File srcFile = new File("D:/test.txt");
            File destFile = new File("D:/dest.txt");

            if (!srcFile.exists()) {
                throw new NoSuchFileException("no such file >>>" + srcFile.getName());
            }
            boolean flag = true;
            if (!destFile.exists()) {
                flag = destFile.createNewFile();
            }
            if (flag) {
                //创建成功
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                channelIn = fis.getChannel();
                channelOut = fos.getChannel();

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (channelIn.read(buffer) != -1) {
                    //buffer翻转 读取模式
                    buffer.flip();
                    int outLength = 0;
                    while ((outLength = channelOut.write(buffer)) != 0) {
                        log.info("写入字节数:>>>{}", outLength);
                    }
                    buffer.clear();
                }
                channelOut.force(true);

            } else {
                log.error("file {} create failed", destFile.getName());
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (channelOut != null) {
                channelOut.close();
            }
            if (channelIn != null) {
                channelIn.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }

        }

    }
}
