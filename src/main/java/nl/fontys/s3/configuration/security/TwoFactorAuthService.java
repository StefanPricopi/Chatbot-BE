package nl.fontys.s3.business.impl;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import nl.fontys.s3.configuration.mailservice.SmtpMailSender;
import nl.fontys.s3.domain.mail.SendMailRequest;
import nl.fontys.s3.persistence.TwoFactorAuthRepository;
import nl.fontys.s3.persistence.entity.TwoFactorAuthEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TwoFactorAuthService {
    private final SmtpMailSender smtpMailSender;
    private final TwoFactorAuthRepository twoFactorAuthRepository;

    public void sendTwoFactorCode(UserEntity user) throws MessagingException {
        String twoFACode = generate2FACode();

        // Save the 2FA code to the database
        TwoFactorAuthEntity twoFactorAuthEntity = new TwoFactorAuthEntity(user.getUserid(), twoFACode);
        twoFactorAuthRepository.save(twoFactorAuthEntity);

        // Create the email body
        String emailBody = "Hello " + user.getUsername() + ", \r\n\r\n" +
                "Your 2FA code to login to BasWorld is: " + twoFACode + ".\r\n" +
                "This code can only be used once. \r\nIf you entered the wrong code, please completely restart the login process.";

        // Send the 2FA code via email
        SendMailRequest request = SendMailRequest.builder()
                .to(user.getEmail())
                .subject("Your BasWorld 2FA Code")
                .body(emailBody)
                .build();

        smtpMailSender.sendMail(request);
    }

    public boolean validateTwoFactorCode(Long userId, String providedCode) {
        Optional<TwoFactorAuthEntity> twoFactorAuthEntityOptional = twoFactorAuthRepository.findById(userId);
        if (twoFactorAuthEntityOptional.isEmpty()) {
            return false;
        }
        TwoFactorAuthEntity twoFactorAuthEntity = twoFactorAuthEntityOptional.get();
        return twoFactorAuthEntity.getCode().equals(providedCode);
    }

    private String generate2FACode() {
        int randomInt = (int) (Math.random() * 900000) + 100000; // Generates a 6-digit random number
        return String.valueOf(randomInt);
    }
}
