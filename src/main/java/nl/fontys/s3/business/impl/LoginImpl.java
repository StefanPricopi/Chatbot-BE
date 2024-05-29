package nl.fontys.s3.business.impl;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.Login;
import nl.fontys.s3.business.exceptions.InvalidCredentialsException;
import nl.fontys.s3.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.configuration.security.token.exception.InvalidTwoFactorCodeException;
import nl.fontys.s3.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.business.impl.TwoFactorAuthService;
import nl.fontys.s3.domain.login.LoginRequest;
import nl.fontys.s3.domain.login.LoginResponse;
import nl.fontys.s3.domain.login.TwoFactorAuthResponse;
import nl.fontys.s3.domain.login.TwoFactorRequest;
import nl.fontys.s3.persistence.TwoFactorAuthRepository;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginImpl implements Login {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    private final TwoFactorAuthService twoFactorAuthService;
    private final TwoFactorAuthRepository twoFactorAuthRepository;

    @Override
    public TwoFactorAuthResponse login(LoginRequest loginRequest) throws MessagingException {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(InvalidCredentialsException::new);
        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // Send the 2FA code
        twoFactorAuthService.sendTwoFactorCode(user);

        return TwoFactorAuthResponse.builder().message("2FA code sent to your email.").build();
    }

    public LoginResponse verifyTwoFactorCode(TwoFactorRequest twoFactorRequest) {
        UserEntity user = userRepository.findByUsername(twoFactorRequest.getUsername())
                .orElseThrow(InvalidCredentialsException::new);

        boolean isValid = twoFactorAuthService.validateTwoFactorCode(user.getUserid(), twoFactorRequest.getTwoFactorCode());
        if (!isValid) {
            twoFactorAuthRepository.deleteById(user.getUserid());
            throw new InvalidTwoFactorCodeException();
        }
        twoFactorAuthRepository.deleteById(user.getUserid());
        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getUserid(), user.getRolesSet().stream().toList()));
    }
}
