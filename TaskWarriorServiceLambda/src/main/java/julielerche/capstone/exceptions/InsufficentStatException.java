package julielerche.capstone.exceptions;

public class InsufficentStatException extends RuntimeException {

    private static final long serialVersionUID = -2287972220933135156L;

    /**
     * Throws the error message.
     */
    public InsufficentStatException(String notEnoughStaminaForAction) {
        super();
    }
}