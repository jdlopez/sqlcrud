package io.github.jdlopez.sqlcrud.exceptions;

/**
 * <p>Database exceptions</p>
 * <p>Extends servletexception to match servlet-rest sservice architecture</p>
 */
public class DatabaseException extends Exception {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
