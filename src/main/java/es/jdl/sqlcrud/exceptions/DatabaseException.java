package es.jdl.sqlcrud.exceptions;

import javax.servlet.ServletException;

/**
 * <p>Database exceptions</p>
 * <p>Extends servletexception to match servlet-rest sservice architecture</p>
 * @author jdlopez
 */
public class DatabaseException extends ServletException {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
