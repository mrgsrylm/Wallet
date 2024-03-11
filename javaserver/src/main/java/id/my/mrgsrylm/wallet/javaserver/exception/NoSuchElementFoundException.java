package id.my.mrgsrylm.wallet.javaserver.exception;

public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException() {
        super();
    }

    public NoSuchElementFoundException(String message) {
        super(message);
    }

    public NoSuchElementFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
