package emailUtil;

import java.util.Date;
import java.io.File;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailUtility {
	
	/**
	 * Authentication required
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @param user
	 * @param password
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body,
			String user, String password) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.addHeader("Content-type", "text/HTML;charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");
			
			message.setFrom(new InternetAddress("DoNotReply@example.com", "NoReply-JD"));
			
			message.setReplyTo(InternetAddress.parse("DoNotReply@example.com", false));
			
			message.setSubject(subject);
			message.setText(body);
			message.setSentDate(new Date());
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail,false));
			Transport.send(message, user, password);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	
	/**
	 * No authentication required
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.addHeader("Content-type", "text/HTML;charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");
			
			message.setFrom(new InternetAddress("DoNotReply@example.com", "NoReply-JD"));
			
			message.setReplyTo(InternetAddress.parse("DoNotReply@example.com", false));
			
			message.setSubject(subject);
			message.setText(body);
			message.setSentDate(new Date());
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail,false));
			Transport.send(message);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static void sendEmail(Session session, String toEmail, String subject, File file) {
		
	}
}
