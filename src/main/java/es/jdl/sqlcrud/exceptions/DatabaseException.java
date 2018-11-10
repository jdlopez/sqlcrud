package es.jdl.sqlcrud.exceptions;

import javax.servlet.ServletException;

/**
 * Database exceptions
 * <br/>Extends servletexception to match servlet-rest sservice architecture
 * @author jdlopez
 */
public class DatabaseException extends ServletException {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
