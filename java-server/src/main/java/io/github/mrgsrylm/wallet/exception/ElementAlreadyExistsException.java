package io.github.mrgsrylm.wallet.exception;

public class ElementAlreadyExistsException extends RuntimeException {

    public ElementAlreadyExistsException() {
        super();
    }

    public ElementAlreadyExistsException(String message) {
        super(message);
    }

    public ElementAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
