package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.exceptions.NotFoundException;
import es.jdl.sqlcrud.services.ConfigHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author jdlopez
 */
public class TableColumnsDefServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String tableName = ConfigHelper.getTableFromURL(req);
        TableDef table = dbService.getTable(tableName);
        if (table == null) {
            throw new NotFoundException("Table " + tableName + " didnt found");
        } else if (table.getColumns() == null) {
            table.setColumns(dbService.findColumns(tableName));
        }
        respondWithObject(resp, table);

    }
}
