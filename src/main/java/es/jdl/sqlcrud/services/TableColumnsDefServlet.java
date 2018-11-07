package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author jdlopez
 */
public class TableColumnsDefServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String tableName = path.substring(path.lastIndexOf('/'));
        try {
            resp.getWriter().print(gson.toJson(dbService.findColumns(tableName)));
        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }

    }
}
