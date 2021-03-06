package rusoft.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Client not owner car")
public class ClientNotOwnerCarException extends RuntimeException {
}
