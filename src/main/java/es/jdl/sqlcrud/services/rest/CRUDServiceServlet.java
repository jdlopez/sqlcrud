package es.jdl.sqlcrud.services.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.services.ConfigHelper;
import es.jdl.sqlcrud.services.DbService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CRUDServiceServlet extends HttpServlet {
    protected CRUDConfiguration config;
    protected DbService dbService;
    protected Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = ConfigHelper.getConfig(config.getServletContext());
        this.dbService = DbService.getInstance(config.getServletContext());
        gson = new GsonBuilder()
                .setDateFormat(this.config.getDateFormatPattern()).create();
    }

    protected void respondWithObject(HttpServletResponse resp, Object o) throws IOException {
        resp.setContentType("application/json");
        // if object is null then 404
        resp.getWriter().print(gson.toJson(o));
    }

    protected void sendException(HttpServletResponse resp, Exception e) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().print(String.format("{\"error\": \"%s\"}", e.getMessage()));
    }

}
