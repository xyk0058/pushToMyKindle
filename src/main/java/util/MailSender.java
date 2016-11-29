package util;

import component.IMailSender;

import java.io.File;  
import java.util.Date;  
import java.util.Properties;  
  
import javax.activation.DataHandler;  
import javax.activation.DataSource;  
import javax.activation.FileDataSource;  
import javax.mail.BodyPart;  
import javax.mail.Message;  
import javax.mail.Multipart;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;  
import javax.mail.internet.MimeMessage;  
import javax.mail.internet.MimeMultipart;  

/*********************************************
 * MailSender.java
 * Author: xyk0058
 * Created on: Nov 29, 2016
 ********************************************/

public class MailSender implements IMailSender {

	
	public void sendFrom(String filePath) {
		// TODO Auto-generated method stub
		try {
            String smtpFromMail = "2603653389@qq.com";  //账号  
            String pwd = "huangfei"; //密码  
            int port = 25; //端口  
            String host = "mail.bjtu.edu.cn"; //邮件服务器
  
            Properties props = new Properties();  
            props.put("mail.smtp.host", host);  
            props.put("mail.smtp.auth", "true");  
            Session session = Session.getDefaultInstance(props);  
            session.setDebug(false);
  
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpFromMail, "QQ邮件测试"));  
            message.addRecipient(Message.RecipientType.TO,  
                    new InternetAddress(toMail));  
            message.setSubject(subject);  
            message.addHeader("charset", "UTF-8");  
              
            /*添加正文内容*/  
                Multipart multipart = new MimeMultipart();  
                BodyPart contentPart = new MimeBodyPart();  
                contentPart.setText(content);  
  
                contentPart.setHeader("Content-Type", "text/html; charset=GBK");  
            multipart.addBodyPart(contentPart);  
              
            /*添加附件*/  
            for (String file : files) {  
                File usFile = new File(file);  
                MimeBodyPart fileBody = new MimeBodyPart();  
                DataSource source = new FileDataSource(file);  
                fileBody.setDataHandler(new DataHandler(source));  
                sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();  
                fileBody.setFileName("=?GBK?B?"  
                        + enc.encode(usFile.getName().getBytes()) + "?=");  
                    multipart.addBodyPart(fileBody);  
                }  
  
                message.setContent(multipart);  
                message.setSentDate(new Date());  
                message.saveChanges();  
                Transport transport = session.getTransport("smtp");  
  
                transport.connect(host, port, smtpFromMail, pwd);  
                transport.sendMessage(message, message.getAllRecipients());  
                transport.close();

        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}

}
