package io.github.jdlopez.sqlcrud.service;

import io.github.jdlopez.sqlcrud.domain.def.ColumnDef;
import io.github.jdlopez.sqlcrud.domain.def.TableDef;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JdbcMapper {

    public static Map<String, Object> resultSetToMap(ResultSet rs, String[] columnNames) throws SQLException {
        HashMap<String, Object> row = new HashMap<>();
        for (String c: columnNames)
            row.put(c, rs.getObject(c));
        return row;
    }

    public static String[] readColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] ret = new String[rsmd.getColumnCount()];
        for (int i = 1; i <= ret.length; i++)
            ret[i - 1] = rsmd.getColumnName(i);
        return ret;
    }


    public static TableDef tableDefFromRS(ResultSet rs, String[] columnNames) throws SQLException {
        TableDef fakeTable = new TableDef();
        List<ColumnDef> columns = new ArrayList<>();
        for (String c: columnNames) {
            ColumnDef cd = new ColumnDef();
            cd.setName(c);
            columns.add(cd);
        }
        fakeTable.setColumns(columns);
        return fakeTable;
    }
}
