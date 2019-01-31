package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.domain.DataListResponse;
import es.jdl.sqlcrud.domain.SelectFilter;
import es.jdl.sqlcrud.services.ConfigHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Lists table data
 * @author jdlopez
 */
public class ListDataServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String t = ConfigHelper.getTableFromURL(req);
            respondWithObject(resp,
                    new DataListResponse(dbService.getTable(t), dbService.selectFromTable(t, new SelectFilter(req)))
            );
        } catch (Exception e) {
            super.sendException(resp, e);
        }
    }
}
