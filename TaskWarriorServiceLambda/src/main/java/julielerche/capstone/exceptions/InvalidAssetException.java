package julielerche.capstone.exceptions;

public class InvalidAssetException extends RuntimeException {
    private static final long serialVersionUID = -7242922136742258370L;
    /**
     * Throws the error message.
     */
    public InvalidAssetException() {
        super();
    }
    /**
     * Throws the error message.
     * @param message the message to display
     */
    public InvalidAssetException(String message) {
        super(message);
    }
    /**
     * Throws the error message.
     * @param message the message to display
     * @param cause the cause of the error
     */
    public InvalidAssetException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Throws the error message.
     * @param cause the cause of the error
     */
    public InvalidAssetException(Throwable cause) {
        super(cause);
    }

    /**
     * Throws the error message.
     * @param message the message.
     * @param cause what caused the error.
     * @param enableSuppression boolean of suppression.
     * @param writableStackTrace boolean of stacktrace.
     */
    protected InvalidAssetException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
