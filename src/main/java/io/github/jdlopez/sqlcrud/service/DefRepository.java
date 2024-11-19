package io.github.jdlopez.sqlcrud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jdlopez.sqlcrud.domain.config.CRUDConfiguration;
import io.github.jdlopez.sqlcrud.domain.config.CRUDDataSource;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class DefRepository {


    private Path confFile;
    private CRUDConfiguration config;

    private ObjectMapper mapper = new ObjectMapper();

    public DefRepository() throws IOException {
        this.reload();
    }

    public CRUDConfiguration getConfig() {
        return config;
    }

    public void save() throws IOException {
        if (!confFile.toFile().exists()) {
            confFile.getParent().toFile().mkdirs();
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(confFile.toFile(), config);
    }

    public void reload() throws IOException {
        if (System.getProperty("sqlcrud.conf") != null) {
            confFile = Paths.get(System.getProperty("sqlcrud.conf"));
        } else {
            confFile = Path.of(System.getProperty("user.home"), ".sqlcrud", "cruddatabase.json");
        }
        if (confFile.toFile().exists()) {
            this.config = mapper.readValue(confFile.toFile(), CRUDConfiguration.class);
        } else {
            // init default values?
            this.config = new CRUDConfiguration();
        }

    }

    public void addDataSource(CRUDDataSource dataSource) {
        this.config.getDataSources().add(dataSource);
    }
}
