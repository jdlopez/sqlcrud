package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.domain.def.ColumnDef;
import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.exceptions.NotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ConfigHelper {
    public static final String CRUDDATABASE_JSON = "cruddatabase.json";

    /** Factory accesor to config object */
    public static CRUDConfiguration getConfig(ServletContext ctx) {
        return (CRUDConfiguration) ctx.getAttribute(CRUDDATABASE_JSON);
    }

    /** tablename must be the last URL variable: pattern=blabla/{table} */
    public static String getTableFromURL(HttpServletRequest req) {
        String path = req.getPathInfo();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public static String getPrimaryKeyValue(HttpServletRequest req, TableDef table) throws NotFoundException {
        if (table == null || table.getColumns() == null)
            throw new NotFoundException("Table or column definition not found: " + table);
        for (ColumnDef c: table.getColumns())
            if (c.isPrimaryKey())
                return req.getParameter(c.getName());
        return null;
    }
}
