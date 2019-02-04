package es.jdl.sqlcrud.services.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AppInfoServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        respondWithObject(resp, Map.of(
                "applicationName", super.config.getApplicationName() != null?super.config.getApplicationName():"CRUD App",
                "applicationHome", super.config.getApplicationHome(),
                "query", super.config.isQueryService(),
                "report", super.config.getReportSource() != null
        ));
    }
}
