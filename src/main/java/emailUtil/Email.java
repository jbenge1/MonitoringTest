package emailUtil;

import java.util.Properties;

import javax.mail.Session;

public class Email {

	//private final String FROM_EMAIL = " 5a74da778d-de6990@inbox.mailtrap.io";
	private final String PASS = "b727e282a14da5";
	private final String TO_EMAIL = "justinbng36@gmail.com";
	private final String USER = "926642549be59c";
	
	
	/**
	 * Consider passing in a parameter that is a string of the file location, 
	 * or the file itself. Then read in the contents of the file which will be 
	 * the message body (subject too?)
	 */
	public void sendEmail(String body) {
		String smtpHostServer = "smtp.mailtrap.io";
		Properties props = System.getProperties();
		
		props.put("mail.smtp.host", smtpHostServer);
		Session session = Session.getInstance(props);
		EmailUtility.sendEmail(session, TO_EMAIL, "TEST", body, USER, PASS);
	}
}
