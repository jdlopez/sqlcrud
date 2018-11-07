package es.jdl.sqlcrud.services;

import com.google.gson.Gson;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.DatabaseException;
import es.jdl.sqlcrud.utils.JsonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllTablesDefServlet extends HttpServlet {
    private CRUDConfiguration config;
    private DbService dbService;
    private Gson gson = new Gson();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = CRUDConfigContextListener.getConfig(config.getServletContext());
        try {
            dbService = new DbService(this.config.getDataSourceName());
        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        // 1. get config
        // 2. check permission
        // 3. get data
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(dbService.allTables));

    }
}
