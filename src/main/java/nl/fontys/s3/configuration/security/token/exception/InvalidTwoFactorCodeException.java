package nl.fontys.s3.configuration.security.token.exception;

public class InvalidTwoFactorCodeException extends RuntimeException {
    public InvalidTwoFactorCodeException() {
        super("Invalid 2FA code provided.");
    }
}
