package util;

import java.io.File;
import java.io.FileInputStream;
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

import component.IMailSender;  

/*********************************************
 * MailSender.java
 * Author: xyk0058
 * Created on: Nov 29, 2016
 ********************************************/

public class MailSender implements IMailSender {

	
	public void sendFrom(String filePath) {
		// TODO Auto-generated method stub'
		
		try {
			
			Properties mailprop = new Properties();//属性集合对象
			FileInputStream fis = new FileInputStream("../pushToMyKindle/src/main/resources/mail.properties");//属性文件流    
			mailprop.load(fis);//将属性文件流装载到Properties对象中
			
            String smtpFromMail = mailprop.getProperty("mail.send_user");  //账号  
            String pwd = mailprop.getProperty("mail.send_pwd"); //密码
            int port = Integer.parseInt(mailprop.getProperty("mail.send_port")); //端口  
            String host = mailprop.getProperty("mail.send_host"); //邮件服务器
            String toMail = mailprop.getProperty("mail.to_user"); //接收邮件地址
  
            Properties props = new Properties();  
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);  
            session.setDebug(false);
  
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpFromMail, "Convert"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toMail));
			message.setSubject("Convert");
            message.addHeader("charset", "UTF-8");

            /*添加正文内容*/  
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("Convert");
            
            contentPart.setHeader("Content-Type", "text/html; charset=GBK");  
            multipart.addBodyPart(contentPart);

            File usFile = new File(filePath);
            MimeBodyPart fileBody = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            fileBody.setDataHandler(new DataHandler(source));
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            fileBody.setFileName("=?GBK?B?"
                    + enc.encode(usFile.getName().getBytes()) + "?=");
            multipart.addBodyPart(fileBody);
  
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
	
	public static void main(String[] args) throws Exception{
		
	}
	
}
