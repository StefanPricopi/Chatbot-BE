package nl.fontys.s3.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
