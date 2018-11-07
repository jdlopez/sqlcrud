package es.jdl.sqlcrud.exceptions;

/**
 * Database exceptions
 * @author jdlopez
 */
public class DatabaseException extends Throwable {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
