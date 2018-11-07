package es.jdl.sqlcrud.services;

import com.google.gson.Gson;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.DatabaseException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class CRUDServiceServlet extends HttpServlet {
    protected CRUDConfiguration config;
    protected DbService dbService;
    protected Gson gson = new Gson();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = CRUDConfigContextListener.getConfig(config.getServletContext());
        try {
            dbService = new DbService(this.config.getDataSourceName());
        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}
