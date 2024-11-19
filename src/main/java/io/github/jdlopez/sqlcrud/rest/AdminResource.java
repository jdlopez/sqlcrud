package io.github.jdlopez.sqlcrud.rest;

import io.github.jdlopez.sqlcrud.domain.config.CRUDConfiguration;
import io.github.jdlopez.sqlcrud.domain.config.CRUDDataSource;
import io.github.jdlopez.sqlcrud.service.DefRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/admin")
public class AdminResource {
    @Inject
    private DefRepository defRepository;

    @GET
    public CRUDConfiguration getCRUDConfiguration() {
        return defRepository.getConfig();
    }

    @POST
    public CRUDConfiguration save() throws IOException {
        defRepository.save();
        return defRepository.getConfig();
    }

    @POST
    @Path("/datasource")
    public CRUDConfiguration addDatasource(CRUDDataSource dataSource) {
        defRepository.addDataSource(dataSource);

        return defRepository.getConfig();
    }

    @GET
    @Path("/datasource/{name}")
    public CRUDDataSource getDatasource(@PathParam("name") String name) {
        return defRepository.getConfig().getDataSource(name);
    }

    @GET
    @Path("/drivers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DriverInfo> getDriverClasses() {
        return DriverManager.drivers().map(DriverInfo::new).collect(Collectors.toList());
    }

    public class DriverInfo {
        private String className;
        private String urlSample;

        public DriverInfo(Driver driver) {
            this.className = driver.getClass().getName();
            switch (className) {
                case "com.mysql.cj.jdbc.Driver": //ConnectionUrl.Type.SINGLE_CONNECTION.getScheme();
                    this.urlSample = "jdbc:mysql://localhost:3306/database";
                    break;
                case "org.postgresql.Driver": // https://jdbc.postgresql.org/documentation/use/#connecting-to-the-database
                    this.urlSample = "jdbc:postgresql://host:port/database";
                    break;
                case "oracle.jdbc.driver.OracleDriver":
                    this.urlSample = "jdbc:oracle:thin:@host:port/database";
                    break;
                case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
                    this.urlSample = "jdbc:sqlserver://host:port/database";
                    break;
                case "org.h2.Driver": // https://www.h2database.com/html/features.html
                    this.urlSample = "jdbc:h2:[file:][<path>]<databaseName> or jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>";
                    break;
                case "org.hsqldb.jdbc.JDBCDriver":
                    this.urlSample = "jdbc:hsqldb:hsql://host:port/database";
                    break;
                default:
                    this.urlSample = null;
            }
        }

        public String getClassName() {
            return className;
        }

        public String getUrlSample() {
            return urlSample;
        }
    }
}
