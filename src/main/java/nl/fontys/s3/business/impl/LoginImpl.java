package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.Login;
import nl.fontys.s3.business.exceptions.InvalidCredentialsException;
import nl.fontys.s3.configuration.security.PasswordEncoderConfig;
import nl.fontys.s3.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.domain.LoginRequest;
import nl.fontys.s3.domain.LoginResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class LoginImpl implements Login {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest){
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername()).get();
        if(user == null){
            throw new InvalidCredentialsException();
        }
        if(!matchesPassword(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException();
        }
        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user){

        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getUserId(), user.getRoles().stream().toList()));
    }



}
