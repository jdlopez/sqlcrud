package es.jdl.sqlcrud.services.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends CRUDServiceServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            respondWithObject(resp, dbService.selectFromQuery(req.getParameter("sql")));
        } catch (Exception e) {
            super.sendException(resp, e);
        }

    }
}
