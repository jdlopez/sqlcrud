package io.github.jdlopez.sqlcrud.rest;

import io.github.jdlopez.sqlcrud.domain.def.TableDef;
import io.github.jdlopez.sqlcrud.exceptions.DatabaseException;
import io.github.jdlopez.sqlcrud.service.DataListResponse;
import io.github.jdlopez.sqlcrud.service.DbService;
import io.github.jdlopez.sqlcrud.service.DefRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@Path("/api")
public class TableResource {

    @Inject
    private DefRepository defRepository;
    @Inject
    private DbService dbService;

    @GET
    @Path("/{datasource}/tables")
    public List<TableDef> getTables(@PathParam("datasource") String datasource) throws DatabaseException {
        return dbService.getTables(datasource);

    }

    @GET
    @Path("/{datasource}/table/{table}")
    public DataListResponse getData(@PathParam("datasource") String datasource, @PathParam("table") String table,
                                    @Context UriInfo uriInfo) throws DatabaseException {
        return new DataListResponse(dbService.getTableDef(datasource, table),
                dbService.selectFromTable(datasource, table));
    }

}
