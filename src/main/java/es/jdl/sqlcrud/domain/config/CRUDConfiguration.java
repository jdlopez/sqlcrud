package es.jdl.sqlcrud.domain.config;

import es.jdl.sqlcrud.domain.def.TableDef;

import java.util.List;

/**
 *
 * @author jdlopez
 */
public class CRUDConfiguration {

    private String dataSourceName;
    private List<TableDef> tables;
    private AutoDiscoveryConfig autoDiscovery;
    private String dateFormatPattern = "dd/MM/yyyy";
    private String applicationName;
    private boolean queryService = false;
    private String reportSource;

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

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isQueryService() {
        return queryService;
    }

    public void setQueryService(boolean queryService) {
        this.queryService = queryService;
    }

    public String getReportSource() {
        return reportSource;
    }

    public void setReportSource(String reportSource) {
        this.reportSource = reportSource;
    }
}
