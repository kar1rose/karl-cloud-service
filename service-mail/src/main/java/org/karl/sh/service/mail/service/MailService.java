package org.karl.sh.service.mail.service;

/**
 * @Author: KARL ROSE
 * @Date: 2020/3/24 12:59
 * @Description:
 **/
public interface MailService {

    void sendMail(String to,String subject,String content);
}
