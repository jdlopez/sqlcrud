package io.github.jdlopez.sqlcrud.domain.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Data
public class CRUDConfiguration {

    private List<CRUDDataSource> dataSources = new ArrayList<>();
    private String applicationName;
    private String applicationHome;
    private boolean queryService = false;
    private String reportSource;

    public CRUDDataSource getDataSource(String name) {
        if (this.dataSources == null || this.dataSources.isEmpty()) {
            return null;
        } else {
            return this.dataSources.stream()
                    .filter(dataSource -> dataSource.getName().equals(name))
                    .findFirst().orElse(null);
        }
    }
}
