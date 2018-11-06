package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.CRUDConfiguration;

import java.io.InputStream;

public interface CRUDConfigBuilder {

    public CRUDConfiguration readConfig(InputStream source) throws ConfigurationException;
}
