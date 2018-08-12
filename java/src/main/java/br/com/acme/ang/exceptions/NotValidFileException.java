package br.com.acme.ang.exceptions;

public class NotValidFileException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final Integer ERROR_CODE = 199;

	public NotValidFileException() {
        super();
    }

    public NotValidFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotValidFileException(String message) {
        super(message);
    }
    public NotValidFileException(Throwable cause) {
        super(cause);
    }
}