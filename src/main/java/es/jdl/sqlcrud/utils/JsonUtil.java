package es.jdl.sqlcrud.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *
 * @author jdlopez
 */
public class JsonUtil {

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

}
