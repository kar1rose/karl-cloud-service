package org.karl;
/**
 * Created by KARL_ROSE on 2020/4/9 23:13
 */

import lombok.extern.slf4j.Slf4j;

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
public class CustomBufferDemo {
    public static void main(String[] args) throws IOException {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File srcFile = new File("D:/test.txt");
            File destFile = new File("D:/dest2.txt");

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
                while (fis.read(bytes) != -1) {
                    fos.write(bytes);
                }

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
