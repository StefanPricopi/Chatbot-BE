package nl.fontys.s3.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNameAlreadyTakenException extends ResponseStatusException {
    public UserNameAlreadyTakenException() {
        super(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_TAKEN");
    }
}
