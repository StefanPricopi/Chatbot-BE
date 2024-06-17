package nl.fontys.s3.business;

import jakarta.mail.MessagingException;
import nl.fontys.s3.domain.login.LoginRequest;
import nl.fontys.s3.domain.login.LoginResponse;
import nl.fontys.s3.domain.login.TwoFactorAuthResponse;
import nl.fontys.s3.domain.login.TwoFactorRequest;


public interface Login {
    TwoFactorAuthResponse login(LoginRequest loginRequest) throws MessagingException;
    LoginResponse verifyTwoFactorCode(TwoFactorRequest twoFactorRequest);
}
