package es.jdl.sqlcrud.domain.def;

import es.jdl.sqlcrud.domain.config.CRUDPermission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jdlopez
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CRUDPermission getPermission() {
        return permission;
    }

    public void setPermission(CRUDPermission permission) {
        this.permission = permission;
    }

    public List<ColumnDef> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDef> columns) {
        this.columns = columns;
    }
}
