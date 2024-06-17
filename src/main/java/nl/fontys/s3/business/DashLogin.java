package nl.fontys.s3.business;

import nl.fontys.s3.domain.login.LoginRequest;
import nl.fontys.s3.domain.login.LoginResponse;

public interface DashLogin {
    LoginResponse loginResponse (LoginRequest loginRequest);
}
