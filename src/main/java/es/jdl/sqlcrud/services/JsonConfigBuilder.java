package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.ConfigurationException;
import es.jdl.sqlcrud.utils.JsonUtil;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jdlopez
 */
public class JsonConfigBuilder implements CRUDConfigBuilder {

    @Override
    public CRUDConfiguration readConfig(InputStream source) throws ConfigurationException {
        JsonReader reader = Json.createReader(source);
        JsonObject jobj = reader.readObject();
        try {
            return JsonUtil.fillBean(jobj, CRUDConfiguration.class);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ConfigurationException("readConfig", e.getMessage(), e);
        }
    }
}
