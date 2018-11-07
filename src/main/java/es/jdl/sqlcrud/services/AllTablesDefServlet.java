package es.jdl.sqlcrud.services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllTablesDefServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        // 1. get config
        // 2. check permission
        // 3. get data
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(dbService.getAllTables()));

    }
}
