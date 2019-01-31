package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.services.ConfigHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ReadDataServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TableDef table = dbService.getTable(ConfigHelper.getTableFromURL(req));
            String key = ConfigHelper.getPrimaryKeyValue(req, table);
            Map<String, Object> row = dbService.findByPK(table, key);
            respondWithObject(resp, row);
        } catch (Exception e) {
            super.sendException(resp, e);
        }

    }
}
