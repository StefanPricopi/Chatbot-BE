package nl.fontys.s3.domain.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwoFactorAuthResponse {
    private String message;
}

