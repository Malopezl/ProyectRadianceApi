/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private static final String FROM = "teamradiance2326@gmail.com";
    private static final String FROMNAME = "Randiance Team";
    private static final String SMTP_USERNAME = "teamradiance2326@gmail.com";
    private static final String SMTP_PASSWORD = "xgfbmcofojsyimfg";
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String SUBJECT = "Test Mail";

    private static String body = String.join(
            System.getProperty("line.separator"),
            "<h1>Radiance Email</h1>"
    );

    private EmailSender() {
    }

    public static void sendMail(String mail, String password) throws UnsupportedEncodingException, MessagingException {
        String to = mail;
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        if (!password.equals("")) {
            body = body.concat(String.join(
                    System.getProperty("line.separator"),
                    "<p>Su contraseña es: " + password + "</p>",
                    "<p>Asegurese de guardar su contraseña y eliminar este correo.</p>"
            ));
        } else {
            body = body.concat(String.join(
                    System.getProperty("line.separator"),
                    "<h3>Este correo es para verificar su cuenta</h3>",
                    "<p>Ingrese al link para realizar el proceso de verificacion de cuenta</p>"
            ));
        }
        body = body.concat(String.join(
                System.getProperty("line.separator"),
                "<p>This email was sent from Radiance®</p>"
        ));
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(SUBJECT);
        msg.setContent(body, "text/html");

        // Send the message.
        try (// Create a transport.
                Transport transport = session.getTransport() // Close and terminate the connection.
        ) {
            LOGGER.info("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            LOGGER.info("Email sent!");
        } catch (MessagingException ex) {
            LOGGER.info("The email was not sent.");
            LOGGER.info("Error message: " + ex.getMessage());
        }
    }

}
