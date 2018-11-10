package es.jdl.sqlcrud.exceptions;

import javax.servlet.ServletException;

/**
 * Not found exception. Usefull to handles table find or columns o catalog, etc
 * <br/>Extends servletexception to match servlet-rest sservice architecture
 * @author jdlopez
 */
public class NotFoundException extends ServletException {

    public NotFoundException(String message) {
        super(message);
    }
}
