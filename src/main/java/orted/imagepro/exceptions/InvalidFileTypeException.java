package orted.imagepro.exceptions;

public class InvalidFileTypeException extends  RuntimeException{
    /**
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
