package nl.fontys.s3.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.fontys.s3.business.Login;
import nl.fontys.s3.configuration.mailservice.SmtpMailSender;
import nl.fontys.s3.domain.login.LoginResponse;
import nl.fontys.s3.domain.login.TwoFactorRequest;
import nl.fontys.s3.domain.mail.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final Login login;

    @Autowired
    SmtpMailSender smtpMailSender;

    @PostMapping()
    public ResponseEntity<LoginResponse> verifyTwoFactorCode(@RequestBody TwoFactorRequest twoFactorRequest) {
        LoginResponse response = login.verifyTwoFactorCode(twoFactorRequest);
        return ResponseEntity.ok(response);
    }
}