package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
                            ResourcesUtil.getDefaultValue(ctx.getInitParameter("cruddatabase.json"), "cruddatabase.json"),
                            ctx));
            ctx.setAttribute("cruddatabase.json", conf);
        } catch (ConfigurationException e) {
            ctx.log(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
