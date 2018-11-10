package es.jdl.sqlcrud.services;

import es.jdl.sqlcrud.domain.SelectFilter;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.domain.def.ColumnDef;
import es.jdl.sqlcrud.domain.def.TableDef;
import es.jdl.sqlcrud.exceptions.DatabaseException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 *
 * @author jdlopez
 */
public class DbService {

    public static final String DB_SERVICE = "DbService";

    protected DataSource dataSource;
    protected Map<String, TableDef> allTables;
    protected List<String> catalogs = new LinkedList<>();
    // TODO make configurable
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public DbService(String dataSourceName) throws DatabaseException {
        Connection conn = null;
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(dataSourceName);
            allTables = findAllTables().stream().collect(
                    Collectors.toMap(TableDef::getName, item->item)
            );
            // TODO this must be application scope!
        } catch (NamingException e) {
            throw new DatabaseException("Getting JNDI: " + dataSourceName + ". " + e.getMessage(), e);
        } // end try
    }

    /** Factory constructor */
    public static DbService getInstance(ServletContext ctx) {
        DbService dbService = (DbService) ctx.getAttribute(DB_SERVICE);
        if (dbService == null) {
            CRUDConfiguration cfg = ConfigHelper.getConfig(ctx);
            synchronized (cfg) {
                try {
                    dbService = new DbService(cfg.getDataSourceName());
                    ctx.setAttribute(DB_SERVICE, dbService);
                } catch (DatabaseException e) {
                    ctx.log(e.getMessage(), e);
                }
            }
        }
        return dbService;
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

    public List<Map<String, Object>> selectFromTable(String tableName, SelectFilter filter) throws DatabaseException {
        List<Map<String, Object>> ret = new LinkedList<>();
        Connection conn = null;
        String sql = null;
        try {
            conn = dataSource.getConnection();
            sql = String.format("select * from %s ", tableName);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String[] columnNames = null;
            while (rs.next()) {
                if (columnNames == null)
                    columnNames = readColumnNames(rs);
                ret.add(resultSetToMap(rs, columnNames));
            } // end while
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new DatabaseException("Executing select: " + sql + ". " + e.getMessage(), e);
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

    public Object insertRow(TableDef table, Map<String, String> data) throws DatabaseException {
        Connection conn = null;
        StringBuilder sql = new StringBuilder();
        Object ret = null;
        try {
            sql.append( "insert into " + table.getName() + " (" );
            int commaCount = 0;
            for (ColumnDef c: table.getColumns()) {
                if (!c.isPrimaryKey()) { // PK auto-generated only? maybe another attribute
                    sql.append(c.getName()).append(",");
                    commaCount++;
                }
            }
            sql.deleteCharAt(sql.length()); // remove last ','
            sql.append(") values (");
            for (int i = 0; i < commaCount; i++)
                sql.append("?,");
            sql.deleteCharAt(sql.length()); // remove last ','
            sql.append(")");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            commaCount = 1;
            for (ColumnDef c: table.getColumns()) {
                if (!c.isPrimaryKey()) { // PK auto-generated only? maybe another attribute
                    sql.append(c.getName()).append(",");
                    ps.setObject(commaCount, data.get(c.getName()));
                    commaCount++;
                }
            }
            ps.executeUpdate();
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                ret = rsKeys.getObject(1);
            }
            rsKeys.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Executing insert: " + sql + ". " + e.getMessage(), e);
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

    private Map<String, Object> resultSetToMap(ResultSet rs, String[] columnNames) throws SQLException {
        HashMap<String, Object> row = new HashMap<>();
        for (String c: columnNames)
            row.put(c, rs.getObject(c));
        return row;
    }

    /** */
    private String[] readColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] ret = new String[rsmd.getColumnCount()];
        for (int i = 1; i <= ret.length; i++)
            ret[i - 1] = rsmd.getColumnName(i);
        return ret;
    }

    public TableDef getTable(String tableName) {
        return allTables.get(tableName);
    }

    public Map<String,Object> findByPK(TableDef table, String primaryKeyValue) throws DatabaseException {
        Map<String, Object> ret = null;
        Connection conn = null;
        String sql = null;
        try {
            conn = dataSource.getConnection();
            ColumnDef pkCol = getColumnPK(table.getColumns());
            PreparedStatement ps = conn.prepareStatement("select * from " + table.getName() + " where " + pkCol.getName() + " = ?");
            ps.setObject(1, convertFromString(primaryKeyValue, pkCol));
            ResultSet rs = ps.executeQuery();
            String[] columnNames = null;
            while (rs.next()) {
                if (columnNames == null)
                    columnNames = readColumnNames(rs);
                ret = resultSetToMap(rs, columnNames);
            } // end while
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new DatabaseException("Executing select: " + sql + ". " + e.getMessage(), e);
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

    private Object convertFromString(String value, ColumnDef col) {
        switch (JDBCType.valueOf(col.getType())) {
            case VARCHAR:
            case NVARCHAR:
                return value;
            case NUMERIC:
            case INTEGER:
            case DOUBLE:
            case BIGINT:
            case DECIMAL:
            case FLOAT:
                return new BigDecimal(value);
            case DATE:
            case TIMESTAMP:
                try {
                    return df.parse(value);
                } catch (ParseException e) {
                    return value;
                }
        }
        return value;
    }

    private ColumnDef getColumnPK(List<ColumnDef> columns) {
        for (ColumnDef c: columns)
            if (c.isPrimaryKey())
                return c;
        // not found!!
        return null;
    }

    public Collection<TableDef> getAllTables() {
        return allTables.values();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<String> getCatalogs() {
        return catalogs;
    }
}
