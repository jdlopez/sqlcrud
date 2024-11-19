package io.github.jdlopez.sqlcrud.rest;

import io.github.jdlopez.sqlcrud.domain.config.CRUDDataSource;
import io.github.jdlopez.sqlcrud.service.DbService;
import io.github.jdlopez.sqlcrud.service.DefRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api")
public class DatasourceResource {

    private static final int TIMEOUT_CHECK = 10;
    @Inject
    private DefRepository defRepository;

    @Inject
    private DbService dbService;

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/datasources")
    public List<String> datasources() {
        List<CRUDDataSource> ds = defRepository.getConfig().getDataSources();
        if (ds != null && !ds.isEmpty()) {
            return ds.stream().map(d -> d.getName()).collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @GET
    @Path("/datasource/{name}/test")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean test(@PathParam("name") String name) {
        CRUDDataSource dsDef = defRepository.getConfig().getDataSource(name);
        if (dsDef == null) {
            return false;
        } else {
            DataSource ds = dbService.getDataSource(dsDef.getName());
            try (Connection conn = ds.getConnection()) {
                return conn.isValid(TIMEOUT_CHECK);
            } catch (SQLException e) {
                // FIXME custom exception
                throw new RuntimeException(e);
            }
        }
    }

}
