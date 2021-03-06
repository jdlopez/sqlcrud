package es.jdl.sqlcrud.services.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns JSON with all configured tables. If none it returns all db tables
 */
public class AllTablesDefServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        respondWithObject(resp, config.getTables());
    }
}
