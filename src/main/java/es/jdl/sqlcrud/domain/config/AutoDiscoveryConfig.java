package es.jdl.sqlcrud.domain.config;

public class AutoDiscoveryConfig {

    private Boolean tableList;
    private String filterRegEx;

    public Boolean getTableList() {
        return tableList;
    }

    public void setTableList(Boolean tableList) {
        this.tableList = tableList;
    }

    public String getFilterRegEx() {
        return filterRegEx;
    }

    public void setFilterRegEx(String filterRegEx) {
        this.filterRegEx = filterRegEx;
    }
}
