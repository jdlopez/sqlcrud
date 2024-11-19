package io.github.jdlopez.sqlcrud.domain.def;

import io.github.jdlopez.sqlcrud.domain.config.CRUDPermission;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
@Data
public class TableDef {
    private String name;
    private String description;
    // jdbc values https://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html#getTables()
    private String catalog;
    private String schema;
    private String type;
    private CRUDPermission permission;
    private List<ColumnDef> columns;

    public TableDef() {}

    /** Data from: DatabaseMetaData#getTables */
    public TableDef(ResultSet rs) throws SQLException {
        this.name    = rs.getString("TABLE_NAME");
        this.catalog = rs.getString("TABLE_CAT");
        this.schema  = rs.getString("TABLE_SCHEM");
        this.type    = rs.getString("TABLE_TYPE");
    }

}
