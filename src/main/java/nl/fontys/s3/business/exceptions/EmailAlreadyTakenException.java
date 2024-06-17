package nl.fontys.s3.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyTakenException extends ResponseStatusException {
    public EmailAlreadyTakenException() { super(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_TAKEN"); }
}
