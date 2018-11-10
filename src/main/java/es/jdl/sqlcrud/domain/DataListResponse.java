package es.jdl.sqlcrud.domain;

import es.jdl.sqlcrud.domain.def.TableDef;

import java.util.List;
import java.util.Map;

public class DataListResponse {
    private final TableDef table;
    private final List<Map<String, Object>> data;

    public DataListResponse(TableDef table, List<Map<String, Object>> list) {
        this.table = table;
        this.data = list;
    }

    public TableDef getTable() {
        return table;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
