package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.DatabaseException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ConfigHelper {
    public static final String CRUDDATABASE_JSON = "cruddatabase.json";
    public static final String DB_SERVICE = "DbService";

    /** Factory accesor to config object */
    public static CRUDConfiguration getConfig(ServletContext ctx) {
        return (CRUDConfiguration) ctx.getAttribute(CRUDDATABASE_JSON);
    }

    public static DbService getDbService(ServletContext ctx) {
        DbService dbService = (DbService) ctx.getAttribute(DB_SERVICE);
        if (dbService == null) {
            try {
                dbService = new DbService(getConfig(ctx).getDataSourceName());
                ctx.setAttribute(DB_SERVICE, dbService);
            } catch (DatabaseException e) {
                ctx.log(e.getMessage(), e);
            }
        }
        return dbService;
    }

    /** tablename must be the last URL variable: pattern=blabla/{table} */
    public static String getTableFromURL(HttpServletRequest req) {
        String path = req.getPathInfo();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
