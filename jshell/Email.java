
package jshell;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Email {
    String username,password,smtp,to,subject,message,port;

    public Email(String username, String password, String smtp, String to, String subject, String message,String port) {
        this.username = username;
        this.password = password;
        this.smtp = smtp;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    
    public void sendEmail(){
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp-relay.gmail.com");
	props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, password);
            }
        });
        
        try{
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(message, "text/html");
            Transport.send(message);
            
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    
}

