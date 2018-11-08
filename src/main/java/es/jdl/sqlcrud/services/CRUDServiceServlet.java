package es.jdl.sqlcrud.services;

import com.google.gson.Gson;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class CRUDServiceServlet extends HttpServlet {
    protected CRUDConfiguration config;
    protected DbService dbService;
    protected Gson gson = new Gson();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = ConfigHelper.getConfig(config.getServletContext());
        this.dbService = ConfigHelper.getDbService(config.getServletContext());
    }
}
