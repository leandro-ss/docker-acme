package br.com.acme.ang.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class NotConfiguredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final Integer ERROR_CODE = 199;

	public NotConfiguredException() {
        super();
    }

    public NotConfiguredException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotConfiguredException(String message) {
        super(message);
    }
    public NotConfiguredException(Throwable cause) {
        super(cause);
    }
}