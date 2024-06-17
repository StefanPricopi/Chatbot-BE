package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.DashLogin;
import nl.fontys.s3.business.exceptions.InvalidCredentialsException;
import nl.fontys.s3.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.domain.login.LoginRequest;
import nl.fontys.s3.domain.login.LoginResponse;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashLoginImpl implements DashLogin {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse loginResponse(LoginRequest request){
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(InvalidCredentialsException::new);
        if(!matchesPassword(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException();
        }else{
            String accessToken = generateAccessToken(user);
            return LoginResponse.builder().accessToken(accessToken).build();
        }
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getUserid(), user.getRolesSet().stream().toList()));
    }
}
