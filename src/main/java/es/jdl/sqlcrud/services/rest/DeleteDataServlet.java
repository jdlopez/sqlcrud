package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.services.ConfigHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteDataServlet extends CRUDServiceServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TableDef table = dbService.getTable(ConfigHelper.getTableFromURL(req));
            String key = ConfigHelper.getPrimaryKeyValue(req, table);
            dbService.deleteByPK(table, key);
        } catch (Exception e) {
            super.sendException(resp, e);
        }
    }
}
