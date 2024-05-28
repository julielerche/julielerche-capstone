package julielerche.capstone.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3048059006259402126L;

    /**
     * Throws the error message.
     *
     * @param message the message to display
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
