package es.jdl.sqlcrud.services;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ResourcesUtil {

    /**
     * Opens a resource lookin for name in: ServletContext, Classpath, Filesystem
     * @param resourceName
     * @param ctx
     * @return Null if not found in anyplace or stream
     */
    public static InputStream getResourceAsStream(String resourceName, ServletContext ctx) {
        if (resourceName == null) // assert? @NotNull?
            throw new IllegalArgumentException("Resource must have value!");
        InputStream is = ctx.getResourceAsStream(resourceName);
        if (is == null)
            is = ResourcesUtil.class.getResourceAsStream(resourceName);
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
     * Fill Bean Data from HashMap using Reflection
     * <br/>Found at: https://www.codeproject.com/Articles/780839/Fill-Bean-Data-from-HashMap-using-Reflection
     * slightly modified (generics)
     * @author Debopam Pal, Software Developer, NIC, India.
     * @param fieldValueMapping Mapping of field & its value where 'key' is the field & 'value' is the value.
     * @param clazz The Bean Class which have to be filled.
     * @return The filled object of that Bean Class.
     */
    public static <T> T fillBean(Map<String, ?> fieldValueMapping, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object obj = null;
        obj = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            for (Map.Entry<String, ?> entry : fieldValueMapping.entrySet()) {
                if (field.getName().equals(entry.getKey())) {
                    field.set(obj, entry.getValue().toString());
                }
            }
        }

        return (T) obj;
    }

    /**
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String getDefaultValue(String value, String defaultValue) {
        if (value == null)
            return defaultValue;
        else
            return value;
    }
}
