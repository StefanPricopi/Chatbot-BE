package nl.fontys.s3.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import nl.fontys.s3.business.Login;
import nl.fontys.s3.domain.login.LoginRequest;
import nl.fontys.s3.domain.login.TwoFactorAuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class LoginController {

    private final Login login;

    @PostMapping()
    public ResponseEntity<TwoFactorAuthResponse> login(@RequestBody LoginRequest loginRequest) throws MessagingException {
        TwoFactorAuthResponse response = login.login(loginRequest);
        return ResponseEntity.ok(response);
    }
} 