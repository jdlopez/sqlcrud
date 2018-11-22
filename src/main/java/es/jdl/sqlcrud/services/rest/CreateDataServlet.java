package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.services.ConfigHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CreateDataServlet extends CRUDServiceServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TableDef table = dbService.getTable(ConfigHelper.getTableFromURL(req));

        Map<String, String> row = new HashMap<>();
        for (Iterator<String> iter = req.getParameterNames().asIterator(); iter.hasNext(); ) {
            String name = iter.next();
            row.put(name, req.getParameter(name));
        }
        Object newKey = dbService.insertRow(table, row);
        if (newKey != null)
            row.put(dbService.getColumnPK(table.getColumns()).getName(), newKey.toString());
        respondWithObject(resp, row);

    }
}
