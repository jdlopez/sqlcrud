package io.github.jdlopez.sqlcrud.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.jdlopez.sqlcrud.domain.def.TableDef;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataListResponse {
    private TableDef table;
    private List<Map<String, Object>> data;
    private String error = null;

    public DataListResponse(TableDef table, List<Map<String, Object>> list) {
        this.table = table;
        this.data = list;
    }

    public DataListResponse(String error) {
        this.error = error;
    }

}
