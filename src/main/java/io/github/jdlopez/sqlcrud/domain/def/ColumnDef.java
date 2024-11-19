package io.github.jdlopez.sqlcrud.domain.def;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ColumnDef {
    private String name;
    private String description;
    private String type;
    private int size;
    private int decimalDigits;
    private Boolean nullable;
    private String defaultValue;
    private boolean autoIncrement;
    private boolean primaryKey;
    private boolean generatedColumn;

    public ColumnDef() {}

    public ColumnDef(ResultSet rsCols) throws SQLException {
        this.name = rsCols.getString("COLUMN_NAME");
        this.description = rsCols.getString("REMARKS");
        this.type = rsCols.getString("TYPE_NAME");
        this.size = rsCols.getInt("COLUMN_SIZE");
        this.decimalDigits = rsCols.getInt("DECIMAL_DIGITS");
        this.nullable = "YES".equalsIgnoreCase(rsCols.getString("IS_NULLABLE"));
        this.defaultValue = rsCols.getString("COLUMN_DEF");
        this.primaryKey = false;
        this.autoIncrement = "YES".equalsIgnoreCase(rsCols.getString("IS_AUTOINCREMENT"));
        this.generatedColumn = "YES".equalsIgnoreCase(rsCols.getString("IS_GENERATEDCOLUMN"));
    }

}
