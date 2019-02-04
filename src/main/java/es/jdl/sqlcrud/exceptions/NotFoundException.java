package es.jdl.sqlcrud.exceptions;

import javax.servlet.ServletException;

/**
 * <p>Not found exception. Usefull to handles table find or columns o catalog, etc</p>
 * <p>Extends servletexception to match servlet-rest sservice architecture</p>
 * @author jdlopez
 */
public class NotFoundException extends ServletException {

    public NotFoundException(String message) {
        super(message);
    }
}
