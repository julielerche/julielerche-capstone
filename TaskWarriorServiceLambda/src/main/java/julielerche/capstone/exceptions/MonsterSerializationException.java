package julielerche.capstone.exceptions;

public class MonsterSerializationException extends RuntimeException {

    private static final long serialVersionUID = -8057108673980392034L;

    /**
     * Throws error message.
     * @param message the message to display
     * @param cause the cause of the error
     */
    public MonsterSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
