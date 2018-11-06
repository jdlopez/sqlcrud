package es.jdl.sqlcrud.utils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourcesUtil {

    /**
     * Opens a resource lookin for name in: WEB-INF ServletContext, Classpath, Filesystem
     * @param resourceName
     * @param ctx
     * @return Null if not found in anyplace or stream
     */
    public static InputStream getResourceAsStream(String resourceName, ServletContext ctx) {
        if (resourceName == null) // assert? @NotNull?
            throw new IllegalArgumentException("Resource must have value!");
        InputStream is = null;
        if (ctx != null)
            is = ctx.getResourceAsStream("/WEB-INF/" + resourceName);
        if (is == null)
            is = ResourcesUtil.class.getResourceAsStream(resourceName);
        if (is == null)
            is = ResourcesUtil.class.getResourceAsStream("/" + resourceName); // lookup in root package
        if (is == null) {
            File f = new File(resourceName);
            if (f.exists()) {
                try {
                    is = new FileInputStream(f);
                } catch (FileNotFoundException e) { // prevoulsy checked, so its impossible
                    is = null; // already null :-)
                }
            } // endif exists
        }

        return is;
    }

    /**
     * Finds config value looking at: servletcontext init param, nowhere else :-)
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getConfigValue(String key, String defaultValue, ServletContext ctx) {
        String value = ctx.getInitParameter(key);
        return (value == null) ? defaultValue : value;
    }
}
