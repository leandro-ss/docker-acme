package br.com.acme.ang.exceptions;

public class NotValidOptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public NotValidOptionException() {
        super();
    }

    public NotValidOptionException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotValidOptionException(String message) {
        super(message);
    }
    public NotValidOptionException(Throwable cause) {
        super(cause);
    }
}