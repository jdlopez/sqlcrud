package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.exceptions.ConfigurationException;
import es.jdl.sqlcrud.utils.ResourcesUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;

/**
 * Configures it all
 * @author jdlopez
 */
public class CRUDConfigContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        CRUDConfigBuilder confBuilder = new JsonConfigBuilder();
        CRUDConfiguration conf = null;
        try {

            conf = confBuilder.readConfig(
                    ResourcesUtil.getResourceAsStream(
                            ResourcesUtil.getConfigValue(ConfigHelper.CRUDDATABASE_JSON, ConfigHelper.CRUDDATABASE_JSON, ctx),
                            ctx));
            // reads table definitions
            ArrayList finalList = new ArrayList<>(); // final list
            // must add contex attrib dbservice needs config -> add param?
            ctx.setAttribute(ConfigHelper.CRUDDATABASE_JSON, conf);
            DbService dbService = DbService.getInstance(ctx);
            if (conf.getTables() != null) {
                for (TableDef t: conf.getTables()) {
                    TableDef tableDb = dbService.getTable(t.getName());
                    if (tableDb != null)
                        finalList.add(tableDb);
                } // for db tables
            } else {
                finalList.addAll(dbService.getAllTables());
            }
            conf.setTables(finalList);
            ctx.setAttribute(ConfigHelper.CRUDDATABASE_JSON, conf);
        } catch (ConfigurationException e) {
            ctx.log(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
