package nl.fontys.s3.domain.login;

import lombok.Data;

@Data
public class TwoFactorRequest {
    private String username;
    private String twoFactorCode;
}
