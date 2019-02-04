package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.services.ReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReportServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReportService reportService = ReportService.getInstance(req.getServletContext());
            respondWithObject(resp, reportService.getReportNames(req.getServletContext()));
        } catch (Exception e) {
            super.sendException(resp, e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReportService reportService = ReportService.getInstance(req.getServletContext());
            String sql = reportService.getSQL(req.getParameter("report"), req.getServletContext());
            if (sql != null)
                respondWithObject(resp, dbService.selectFromQuery(sql));
            else
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            super.sendException(resp, e);
        }
    }
}
