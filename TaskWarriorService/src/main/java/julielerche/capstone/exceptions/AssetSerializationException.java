package julielerche.capstone.exceptions;

public class AssetSerializationException extends RuntimeException{

    private static final long serialVersionUID = -4933151743451949932L;

    public AssetSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
