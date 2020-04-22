package org.karl.sh.service.mail.service;

/**
 * @author KARL ROSE
 * @date 2020/3/24 12:59
 * :
 **/
public interface MailService {

    void sendMail(String to,String subject,String content);
}
