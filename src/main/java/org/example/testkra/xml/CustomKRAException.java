package org.example.testkra.xml;

public class CustomKRAException extends Exception {
    public CustomKRAException() {
        super();
    }

    public CustomKRAException(String message) {
        super(message);
    }

    public CustomKRAException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomKRAException(Throwable cause) {
        super(cause);
    }

    protected CustomKRAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
