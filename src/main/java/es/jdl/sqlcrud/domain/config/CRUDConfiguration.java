package es.jdl.sqlcrud.domain.config;

import java.util.List;

/**
 *
 * @author jdlopez
 */
public class CRUDConfiguration {

    private String dataSourceName;
    private List<TableDef> tables;
    private AutoDiscoveryConfig autoDiscovery;

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public List<TableDef> getTables() {
        return tables;
    }

    public void setTables(List<TableDef> tables) {
        this.tables = tables;
    }

    public AutoDiscoveryConfig getAutoDiscovery() {
        return autoDiscovery;
    }

    public void setAutoDiscovery(AutoDiscoveryConfig autoDiscovery) {
        this.autoDiscovery = autoDiscovery;
    }
}
