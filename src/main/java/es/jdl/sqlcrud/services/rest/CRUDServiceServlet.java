package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.services.ConfigHelper;
import es.jdl.sqlcrud.services.DbService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class CRUDServiceServlet extends HttpServlet {
    protected CRUDConfiguration config;
    protected DbService dbService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = ConfigHelper.getConfig(config.getServletContext());
        this.dbService = DbService.getInstance(config.getServletContext());
    }
}
