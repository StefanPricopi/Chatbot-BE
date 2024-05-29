package nl.fontys.s3.configuration.mailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import nl.fontys.s3.domain.mail.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(SendMailRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);//true indicates multipart message

        helper.setFrom("basworldms@outlook.com"); // <--- THIS IS IMPORTANT

        helper.setSubject(request.getSubject());
        helper.setTo(request.getTo());
        helper.setText(request.getBody(), true);//true indicates body is html
        javaMailSender.send(message);
    }
}