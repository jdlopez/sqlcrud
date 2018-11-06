package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

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
            return ResourcesUtil.fillBean(jobj, CRUDConfiguration.class);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ConfigurationException("readConfig", e.getMessage(), e);
        }
    }
}
