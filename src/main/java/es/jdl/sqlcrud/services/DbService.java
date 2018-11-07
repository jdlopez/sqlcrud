package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.def.ColumnDef;
import es.jdl.sqlcrud.domain.def.TableDef;
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
            allTables = findAllTables(); // TODO this must be application scope!
        } catch (NamingException e) {
            throw new DatabaseException("Getting JNDI: " + dataSourceName + ". " + e.getMessage(), e);
        } // end try
    }

    public List<TableDef> findAllTables() throws DatabaseException {
        List<TableDef> ret =  new LinkedList<>();
        DatabaseMetaData md = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
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
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new DatabaseException("Closing db connection: " + conn + ". " + e.getMessage(), e);
                }
            } // conn <> null
        }
        return ret;
    }

    public List<ColumnDef> findColumns(String tableName) throws DatabaseException {
        LinkedList<ColumnDef> columns = new LinkedList<>();
        DatabaseMetaData md = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            md = conn.getMetaData();
            ResultSet rsCols = md.getColumns(null, null, tableName, null);
            while (rsCols.next()) {
                ColumnDef column = new ColumnDef(rsCols);
                columns.add(column);
            } // end while
            rsCols.close();
            ResultSet rsKeys = md.getPrimaryKeys(null, null, tableName);
            while (rsKeys.next()) {
                for (ColumnDef c: columns)
                    if (c.getName().equalsIgnoreCase(rsKeys.getString("COLUMN_NAME")))
                        c.setPrimaryKey(true);

            } // end while
            rsKeys.close();
        } catch (SQLException e) {
            throw new DatabaseException("Getting tables from metadata: " + md + ". " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new DatabaseException("Closing db connection: " + conn + ". " + e.getMessage(), e);
                }
            } // conn <> null
        }
        return columns;
    }

    public List<TableDef> getAllTables() {
        return allTables;
    }
}
