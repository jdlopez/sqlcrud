package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.config.TableDef;
import es.jdl.sqlcrud.exceptions.DatabaseException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * @author jdlopez
 */
public class DbService {

    protected DataSource dataSource;
    protected List<TableDef> allTables;
    protected List<String> catalogs = new LinkedList<>();

    public DbService(String dataSourceName) throws DatabaseException {
        Connection conn = null;
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(dataSourceName);
            conn = dataSource.getConnection();
            allTables = findAllTables(conn);
        } catch (NamingException e) {
            throw new DatabaseException("Getting JNDI: " + dataSourceName + ". " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new DatabaseException("Getting tables using: " + dataSource + ". " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new DatabaseException("Closing db connection: " + conn + ". " + e.getMessage(), e);
                }
            } // conn <> null
        } // end try
    }

    public List<TableDef> findAllTables(Connection conn) throws DatabaseException {
        DatabaseMetaData md = null;
        List<TableDef> ret =  new LinkedList<>();
        try {
            md = conn.getMetaData();
            ResultSet rs = md.getCatalogs();
            while (rs.next()) {
                String cat = rs.getString("TABLE_CAT");
                catalogs.add(cat);
            } // while catalogs
            rs.close();
            ResultSet rsTables = md.getTables(null, null, "%", new String[]{"TABLE"});
            while (rsTables.next()) {
                TableDef table = new TableDef(rsTables);
                ret.add(table);
            } // while tables
            rsTables.close();

        } catch (SQLException e) {
            throw new DatabaseException("Getting tables from metadata: " + md + ". " + e.getMessage(), e);
        }
        return ret;
    }

}
