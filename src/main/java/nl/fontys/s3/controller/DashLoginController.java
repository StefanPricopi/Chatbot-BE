package nl.fontys.s3.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.s3.business.DashLogin;
import nl.fontys.s3.business.Login;
import nl.fontys.s3.domain.login.LoginRequest;
import nl.fontys.s3.domain.login.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashlogin")
@RequiredArgsConstructor
public class DashLoginController {
    private final DashLogin dashLogin;

    @PostMapping()
    public ResponseEntity<LoginResponse> dashLogin(@RequestBody LoginRequest request){
        LoginResponse response = dashLogin.loginResponse(request);
        return ResponseEntity.ok(response);
    }
}
