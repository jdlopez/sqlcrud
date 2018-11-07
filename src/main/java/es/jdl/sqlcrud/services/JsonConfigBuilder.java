package es.jdl.sqlcrud.services;

import com.google.gson.Gson;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.ConfigurationException;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author jdlopez
 */
public class JsonConfigBuilder implements CRUDConfigBuilder {

    private Gson gson = new Gson();

    @Override
    public CRUDConfiguration readConfig(InputStream source) throws ConfigurationException {
        return gson.fromJson(new InputStreamReader(source), CRUDConfiguration.class);
    }
}
