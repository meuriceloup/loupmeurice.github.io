import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class SaveState {

	private static Date actuelle = new Date();
	private static String properties = System.getProperty("user.dir" ) +"/data/smtp.properties";

	/**
	 * Ressource contenant les éléments statiques liés à la création et l'envoi
	 * d'un email.
	 */
	private static Properties smtpBundle = new Properties();

	/**
	 * Envoi d'un email utilisant une socket SSL (SSLSocketFactory).
	 * 
	 * @param to
	 *            celui ou ceux qui doivent recevoir l'email (séparation des
	 *            adresses par des virgules)
	 * @param subject
	 *            sujet de l'email
	 * @param content
	 *            contenu de l'email
	 * @throws AddressException
	 *             les adresses de destinations sont incorrectes
	 * @throws MessagingException
	 *             une erreur est survenue à l'envoi de l'email
	 * 
	 */
	public static void main(String[] arsg) {
		try {
			smtpBundle.load(new FileInputStream(properties));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Le fichier smtp.properties est introuvable", "Erreur", 0);
			System.exit(1);
		}
		try {

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ==> HH:mm");
			String date = dateFormat.format(actuelle);
			SaveState
					.sendEmailSSL(smtpBundle.getProperty("mail.to.user"), smtpBundle.getProperty("mail.subject"), date);
			System.out.println(date);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de l'envoi du répertoire. Votre répertoire n'a pas être envoyé.", "Erreur", 0);
			System.exit(1);
		}
		JOptionPane.showMessageDialog(null, "Votre répertoire a bien été sauvegardé par mail", "Sauvegarde réussie", 1);
	}

	public static void sendEmailSSL(String to, String subject, String content) throws AddressException,
			MessagingException {
		// smtp properties
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpBundle.getProperty("mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port", smtpBundle.getProperty("mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class", smtpBundle.getProperty("mail.smtp.socketFactory.class"));
		props.put("mail.smtp.auth", smtpBundle.getProperty("mail.smtp.auth"));
		props.put("mail.smtp.port", smtpBundle.getProperty("mail.smtp.port"));
		// authentification
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpBundle.getProperty("mail.session.user"), smtpBundle
						.getProperty("mail.session.pass"));
			}
		});
		// construct message
		Message message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		Multipart mp = new MimeMultipart();
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setContent(content, "text/plain");
		mp.addBodyPart(mbp1);
		File f = new File(smtpBundle.getProperty("mail.file"));// impossible bien sur et caste
												// impossible
		if(!f.exists()){
			JOptionPane.showMessageDialog(null, "Le fichier "+smtpBundle.getProperty("mail.file")+" est inexistant", "Erreur", 0);
			System.exit(1);
		}
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setFileName(f.getName());
		mbp.setDataHandler(new DataHandler(new FileDataSource(f)));
		mp.addBodyPart(mbp);
		message.setContent(mp);
		// message.setContent(content, "text/html; charset=ISO-8859-1");
		// send email
		Transport.send(message);
	}
}