package es.jdl.sqlcrud.domain.config;

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
}
