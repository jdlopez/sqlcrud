package es.jdl.sqlcrud.services;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Something like Tomcat's default servlet applied to resources (in classpath)
 * @author jdlopez
 */
public class ResourceServlet extends HttpServlet {

    private String baseResources;
    private String uriMapping; // if there is more than one we take 1st

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Collection<String> maps = config.getServletContext().getServletRegistration(config.getServletName()).getMappings();
        if (maps != null && !maps.isEmpty()) { // just 1st mapping!!
            uriMapping = maps.iterator().next();
            // it must end with /*
            uriMapping = uriMapping.substring(0, uriMapping.lastIndexOf("/*"));
        }
        // else error?
        this.baseResources = config.getInitParameter("baseResources");
        if (this.baseResources == null)
            this.baseResources = "/sqlcrudtemplates";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || "".equals(pathInfo) || "/".equals(pathInfo) ) {
            pathInfo = "index.html"; // aka welcome-page: TODO add configuration
        }
        String resource = baseResources + pathInfo;
        InputStream inputStream = this.getClass().getResourceAsStream(resource);
        inputStream.transferTo(resp.getOutputStream());
        resp.getOutputStream().flush();
        inputStream.close();
    }
}
