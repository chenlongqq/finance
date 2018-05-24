package com.waben.stock.applayer.operation.warpper.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author Created by yuyidi on 2018/3/3.
 * @desc
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String username;

    public void send(String subject, List<String> attachments, String to) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress(username, "惠普优选"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setSentDate(new Date());
            //容器类，可以包含多个MimeBodyPart对象
//            Multipart mp = new MimeMultipart();
            // 生成spring的helper类，赋值收件人、发件人、抄送、主题等
//            MimeBodyPart body = new MimeBodyPart();
//            for (String attachment : attachments) {
//                body.attachFile(attachment);
//            }
//            message.setContent(mp);
//            Transport.send(message);
            helper.setText("请查阅附件");
            for (String attachment : attachments) {
                File file = new File(attachment);
                String fileName = file.getName();
                helper.addAttachment(fileName, file);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String subject, String content, String to) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(new InternetAddress(username, "惠普优选"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setSentDate(new Date());
            helper.setText(content);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }
}
