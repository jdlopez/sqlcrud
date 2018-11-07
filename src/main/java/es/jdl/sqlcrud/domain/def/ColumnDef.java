package es.jdl.sqlcrud.domain.def;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnDef {
    private String name;
    private String description;
    private String type;
    private int size;
    private int decimalDigits;
    private Boolean nullable;
    private String defaultValue;
    private boolean primaryKey;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
