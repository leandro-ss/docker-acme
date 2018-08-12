package br.com.acme.ang.exceptions;

public class NotValidPassException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final Integer ERROR_CODE = 199;

	public NotValidPassException() {
        super();
    }

    public NotValidPassException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotValidPassException(String message) {
        super(message);
    }
    public NotValidPassException(Throwable cause) {
        super(cause);
    }
}