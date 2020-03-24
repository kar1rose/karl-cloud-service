package org.karl.sh.service.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.karl.sh.service.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceMailApplicationTests {

    @Autowired
    private MailService mailService;

    @Test
    public void contextLoads() {
        mailService.sendMail("yifanluo918@gmail.com", "邮件发送测试", "内容测试");
    }

}
