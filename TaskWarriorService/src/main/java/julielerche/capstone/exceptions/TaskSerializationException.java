package julielerche.capstone.exceptions;

public class TaskSerializationException extends RuntimeException {

    private static final long serialVersionUID = 587529221901148449L;

    /**
     * Throws the error message.
     * @param message the message to display
     * @param cause the cause of the error
     */
    public TaskSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
