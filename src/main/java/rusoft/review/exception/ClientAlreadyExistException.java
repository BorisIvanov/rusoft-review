package rusoft.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Client already exist")
public class ClientAlreadyExistException extends RuntimeException {
}
