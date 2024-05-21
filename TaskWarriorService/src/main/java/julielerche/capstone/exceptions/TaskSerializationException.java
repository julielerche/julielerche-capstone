package julielerche.capstone.exceptions;

public class TaskSerializationException extends RuntimeException{

    private static final long serialVersionUID = 587529221901148449L;

    public TaskSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
