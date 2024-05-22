package julielerche.capstone.exceptions;

public class AssetSerializationException extends RuntimeException {

    private static final long serialVersionUID = -4933151743451949932L;

    /**
     * Throws error message.
     * @param message the message to display
     * @param cause the cause of the error
     */
    public AssetSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
