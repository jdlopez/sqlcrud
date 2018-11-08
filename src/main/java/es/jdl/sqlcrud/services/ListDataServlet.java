package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.SelectFilter;
import es.jdl.sqlcrud.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListDataServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = ConfigHelper.getTableFromURL(req);
        try {
            SelectFilter filter = new SelectFilter();
            resp.getWriter().print(gson.toJson(dbService.selectFromTable(tableName, filter)));
        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }

    }
}
