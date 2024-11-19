package io.github.jdlopez.sqlcrud.rest;

import io.github.jdlopez.sqlcrud.service.DataListResponse;
import io.github.jdlopez.sqlcrud.exceptions.DatabaseException;
import io.github.jdlopez.sqlcrud.service.DbService;
import io.github.jdlopez.sqlcrud.service.DefRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class QueryResource {
    @Inject
    private DefRepository defRepository;
    @Inject
    private DbService dbService;

    @POST
    @Path("/{datasource}/sql")
    @Consumes(MediaType.TEXT_PLAIN)
    public DataListResponse query(@PathParam("datasource") String datasource, String query) throws DatabaseException {
        return dbService.executeQuery(datasource, query);
    }
}
