package auth.backend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SignInFailedException extends RuntimeException {
    public SignInFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignInFailedException(String message) {
        super(message);
    }
}

