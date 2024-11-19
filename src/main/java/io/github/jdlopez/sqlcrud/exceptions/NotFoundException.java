package io.github.jdlopez.sqlcrud.exceptions;

/**
 * <p>Not found exception. Usefull to handles table find or columns o catalog, etc</p>
 * <p>Extends servletexception to match servlet-rest sservice architecture</p>
 * @author jdlopez
 */
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }
}
