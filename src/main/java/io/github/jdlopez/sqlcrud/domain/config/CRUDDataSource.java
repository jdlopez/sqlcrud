package io.github.jdlopez.sqlcrud.domain.config;

import io.github.jdlopez.sqlcrud.domain.def.TableDef;
import lombok.Data;

import java.util.List;

@Data
public class CRUDDataSource {
    private String name;
    private String driver;
    private String url;
    private String username;
    private String password;
    private List<TableDef> tables;
    private AutoDiscoveryConfig autoDiscovery;
    private String dateFormatPattern = "dd/MM/yyyy";

}
