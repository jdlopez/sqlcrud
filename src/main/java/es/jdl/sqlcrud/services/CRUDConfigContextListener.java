package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.ConfigurationException;
import es.jdl.sqlcrud.utils.ResourcesUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Configures it all
 * @author jdlopez
 */
public class CRUDConfigContextListener implements ServletContextListener {

    public static final String CRUDDATABASE_JSON = "cruddatabase.json";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        CRUDConfigBuilder confBuilder = new JsonConfigBuilder();
        CRUDConfiguration conf = null;
        try {

            conf = confBuilder.readConfig(
                    ResourcesUtil.getResourceAsStream(
                            ResourcesUtil.getConfigValue(CRUDDATABASE_JSON, CRUDDATABASE_JSON, ctx),
                            ctx));
            ctx.setAttribute(CRUDDATABASE_JSON, conf);
        } catch (ConfigurationException e) {
            ctx.log(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
