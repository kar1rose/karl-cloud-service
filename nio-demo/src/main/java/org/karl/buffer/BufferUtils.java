package org.karl.buffer;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;

/**
 * CustomBuffer
 *
 * @author ROSE
 * @date 2020/4/9 23:13
 **/
@Slf4j
public class BufferUtils {

    /**
     * @param src  源文件路径
     * @param dest 目标文件路径
     * @author KARL.ROSE
     * @date 2020/4/22 10:41
     **/
    public static void copyFileByChannel(String src, String dest) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel channelIn = null;
        FileChannel channelOut = null;

        try {
            File srcFile = new File(src);
            File destFile = new File(dest);

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

    /**
     * @param src  源文件路径
     * @param dest 目标文件路径
     * @author KARL.ROSE
     * @date 2020/4/22 10:43
     **/
    public static void copyFile(String src, String dest) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File srcFile = new File(src);
            File destFile = new File(dest);
            StringBuilder sb = new StringBuilder();

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

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, length, StandardCharsets.UTF_8));
                }

                byte[] b = sb.toString().getBytes();
                fos.write(b);

            } else {
                log.error("file {} create failed", destFile.getName());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }

        }
    }
}
