package es.jdl.sqlcrud.services;

import com.google.gson.Gson;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.ConfigurationException;
import es.jdl.sqlcrud.utils.ResourcesUtil;

import javax.servlet.ServletContext;
import java.io.InputStreamReader;
import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReportService {

    private static final String REPORT_SERVICE = "REPORT_SERVICE";

    private static final int TYPE_NONE = -1;
    private static final int TYPE_JSON = 0;
    private static final int TYPE_TABLE = 1;
    private static final int TYPE_PROPERTIES = 2;

    private int type = TYPE_NONE;
    private String reportSource;

    public ReportService(String reportSource) {
        if (reportSource != null) {
            if (reportSource.startsWith("table")) {
                this.reportSource = reportSource.substring("table:".length());
                this.type = TYPE_TABLE;
            } else if (reportSource.startsWith("properties")) {
                this.reportSource = reportSource.substring("properties:".length());
                this.type = TYPE_TABLE;
            } else if (reportSource.startsWith("json")) {
                this.reportSource = reportSource.substring("json:".length());
                this.type = TYPE_TABLE;
            }
        }
    }

    public static ReportService getInstance(ServletContext ctx) {
        ReportService svc = (ReportService) ctx.getAttribute(REPORT_SERVICE);
        if (svc == null) {
            CRUDConfiguration cfg = ConfigHelper.getConfig(ctx);
            synchronized (cfg) {
                svc = new ReportService(cfg.getReportSource());
            }
        }
        return svc;
    }

    /** Loads all report definition data and lookup to sql */
    public String getSQL(String reportName, ServletContext ctx) throws ConfigurationException {
        String retSQL = null;
        try {
            switch (type) {
                case TYPE_TABLE:
                    Connection conn = null;
                    try {
                        DbService dbService = DbService.getInstance(ctx);
                        conn = dbService.getDataSource().getConnection();
                        String sql = null;
                        if (reportSource.toLowerCase().contains(" from ")) { // its a query!
                            sql = reportSource;
                        } else { // just table name
                            sql = "select sql from " + reportSource + " where name = ?";
                        }
                        PreparedStatement st = conn.prepareStatement(sql);
                        st.setString(1, reportName);
                        ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                            retSQL = rs.getString(1);
                        }
                        rs.close();
                        st.close();
                    } finally {
                        if (conn != null)
                            conn.close();
                    }
                    break;
                case TYPE_PROPERTIES:
                    Properties p = new Properties();
                    p.load(ResourcesUtil.getResourceAsStream(reportSource, ctx));
                    retSQL = p.getProperty(reportName);
                    break;
                case TYPE_JSON:
                    Gson gson = new Gson();
                    MyMap map = gson.fromJson(new InputStreamReader(ResourcesUtil.getResourceAsStream(reportSource, ctx))
                            , MyMap.class);
                    retSQL = map.get(reportName);
            } // end case
        } catch (Exception e) {
            throw new ConfigurationException(reportSource + "-" + reportName, e.getMessage(), e);
        } // end try
        return retSQL;
    }
    private static class MyMap extends HashMap<String,String> {};
}
