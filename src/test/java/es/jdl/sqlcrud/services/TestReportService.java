package es.jdl.sqlcrud.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestReportService {

    @Test
    public void testGetSqlConfig() {
        String tableName = "MY_CONF_TABLE";
        ReportService reportService = new ReportService("table:" + tableName);
        Assertions.assertTrue( reportService.getReportSource().equals(tableName) );
        String sql = reportService.getSqlReportName();
        System.out.println("getSqlReportName: " + sql);
        Assertions.assertTrue( sql.equalsIgnoreCase("select sql from " + tableName + " where name = ?"));
        sql = reportService.getSqlReportList();
        System.out.println("getSqlReportList: " + sql);
        Assertions.assertTrue( sql.equalsIgnoreCase("select name from " + tableName));
    }
}
