package io.github.jdlopez.sqlcrud.service;

import com.zaxxer.hikari.HikariDataSource;
import io.github.jdlopez.sqlcrud.domain.config.CRUDDataSource;
import io.github.jdlopez.sqlcrud.domain.def.ColumnDef;
import io.github.jdlopez.sqlcrud.domain.def.TableDef;
import io.github.jdlopez.sqlcrud.exceptions.DatabaseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DbService {
    @Inject
    private DefRepository defRepository;

    private Map<String, DataSource> dataSources = new HashMap<>();

    public DataSource getDataSource(String name) {
        if (!dataSources.containsKey(name)) {
            CRUDDataSource dsDef = defRepository.getConfig().getDataSource(name);
            if (dsDef != null) {}
                dataSources.put(name, buildDataSource(dsDef));
        }
        return dataSources.get(name);
    }

    private DataSource buildDataSource(CRUDDataSource def) {
        HikariDataSource ret = new HikariDataSource();
        ret.setJdbcUrl(def.getUrl());
        ret.setUsername(def.getUsername());
        ret.setPassword(def.getPassword());
        ret.setDriverClassName(def.getDriver());
        return ret;
    }

    public List<TableDef> getTables(String datasource) throws DatabaseException {
        DataSource dataSource = getDataSource(datasource);
        List<TableDef> ret =  new LinkedList<>();
        DatabaseMetaData md = null;
        try (Connection conn = dataSource.getConnection()) {
            md = conn.getMetaData();
            ResultSet rs = md.getCatalogs();
            while (rs.next()) {
                String cat = rs.getString("TABLE_CAT");
                //catalogs.add(cat);
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

    public TableDef getTableDef(String datasource, String table) throws DatabaseException {
        DataSource dataSource = getDataSource(datasource);
        TableDef ret = null;
        DatabaseMetaData md = null;
        try (Connection conn = dataSource.getConnection()) {
            md = conn.getMetaData();
            ResultSet rs = md.getCatalogs();
            while (rs.next()) {
                String cat = rs.getString("TABLE_CAT");
                //catalogs.add(cat);
            } // while catalogs
            rs.close();
            ResultSet rsTables = md.getTables("%", "%", table, new String[]{"TABLE"});
            while (rsTables.next()) {
                ret = new TableDef(rsTables);
            } // while tables
            rsTables.close();

        } catch (SQLException e) {
            throw new DatabaseException("Getting tables from metadata: " + md + ". " + e.getMessage(), e);
        }
        return ret;
    }

    public DataListResponse executeQuery(String datasource, String sql) throws DatabaseException {
        DataListResponse ret = null;
        DataSource dataSource = getDataSource(datasource);
        try (Connection conn = dataSource.getConnection()) {
            Statement st = conn.createStatement();
            boolean res = st.execute(sql);
            if (!res) {
                ret = new DataListResponse("Updated %d rows".formatted(st.getUpdateCount()));
            } else {
                ResultSet rs = st.getResultSet();
                List<Map<String, Object>> rows = new LinkedList<>();
                String[] cols = null;
                while (rs.next()) {
                    if (cols == null)
                        cols = JdbcMapper.readColumnNames(rs);
                    rows.add(JdbcMapper.resultSetToMap(rs, cols));
                } // end while
                ret = new DataListResponse(JdbcMapper.tableDefFromRS(rs, cols), rows);
            }
            return ret;
        } catch (SQLException e) {
            throw new DatabaseException("Executing query " + sql + ". " + e.getMessage(), e);
        }
    }

    public List<ColumnDef> findColumns(String datasource, String tableName) throws DatabaseException {

        LinkedList<ColumnDef> columns = new LinkedList<>();
        DatabaseMetaData md = null;
        DataSource dataSource = getDataSource(datasource);
        try (Connection conn = dataSource.getConnection()) {
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
        }
        return columns;
    }

    public List<Map<String, Object>> selectFromTable(String datasource, String tableName) throws DatabaseException {
        List<Map<String, Object>> ret = new LinkedList<>();
        String sql = null;
        DataSource dataSource = getDataSource(datasource);
        try (Connection conn = dataSource.getConnection()) {
            sql = String.format("select * from %s ", tableName);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String[] columnNames = null;
            while (rs.next()) {
                if (columnNames == null)
                    columnNames = JdbcMapper.readColumnNames(rs);
                ret.add(JdbcMapper.resultSetToMap(rs, columnNames));
            } // end while
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new DatabaseException("Executing select: " + sql + ". " + e.getMessage(), e);
        }
        return ret;
    }

}
