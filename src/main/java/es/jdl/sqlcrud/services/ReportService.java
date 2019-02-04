package es.jdl.sqlcrud.services;

import com.google.gson.Gson;
import es.jdl.sqlcrud.domain.config.CRUDConfiguration;
import es.jdl.sqlcrud.exceptions.ConfigurationException;
import es.jdl.sqlcrud.utils.ResourcesUtil;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
                    retSQL = getRepositorySqlCommand(ctx, reportName);
                    break;
                case TYPE_PROPERTIES:
                    retSQL = getRepositoryProperties(ctx).getProperty(reportName);
                    break;
                case TYPE_JSON:
                    MyMap map = getRepositoryJson(ctx);
                    retSQL = map.get(reportName);
            } // end case
        } catch (Exception e) {
            throw new ConfigurationException(reportSource + "-" + reportName, e.getMessage(), e);
        } // end try
        return retSQL;
    }

    /**
     * return all report names stored
     */
    public List<String> getReportNames(ServletContext ctx) throws ConfigurationException {
        List<String> ret = new ArrayList<>();
        try {
            switch (type) {
                case TYPE_TABLE:
                    ret = getRepositorySqlList(ctx);
                    break;
                case TYPE_PROPERTIES:
                     ret.addAll(getRepositoryProperties(ctx).stringPropertyNames());
                     break;
                case TYPE_JSON:
                    MyMap map = getRepositoryJson(ctx);
                    ret.addAll(map.keySet());
            } // end case
        } catch (Exception e) {
            throw new ConfigurationException(reportSource, e.getMessage(), e);
        } // end try
        return ret;
    }

    public String getReportSource() {
        return reportSource;
    }

    protected String getSqlReportName() {
        String sql = null;
        if (reportSource.toLowerCase().contains(" from ")) { // its a query!
            sql = reportSource.substring(0, reportSource.indexOf(":"));
        } else { // just table name
            sql = "select sql from " + reportSource + " where name = ?";
        }
        return sql;
    }

    protected String getSqlReportList() {
        String sql = null;
        if (reportSource.toLowerCase().contains(" from ")) { // its a query!
            int idx = reportSource.lastIndexOf(":");
            sql = reportSource.substring(idx + 1);
        } else { // just table name
            sql = "select name from " + reportSource;
        }
        return sql;
    }

    // get report repository ----------------------

    protected String getRepositorySqlCommand(ServletContext ctx, String reportName) throws SQLException {
        ResultSetHandlerString rsString = new ResultSetHandlerString(1);

        executeQuery(ctx, rsString, getSqlReportName(), reportName);
        return rsString.getString();
    }

    private List<String> getRepositorySqlList(ServletContext ctx) throws SQLException {
        ResultSetHandlerList rsh = new ResultSetHandlerList(1);
        executeQuery(ctx, rsh, getSqlReportList());
        return rsh.getList();
    }

    protected MyMap getRepositoryJson(ServletContext ctx) {
        Gson gson = new Gson();
        MyMap map = gson.fromJson(new InputStreamReader(ResourcesUtil.getResourceAsStream(reportSource, ctx))
                , MyMap.class);
        return map;
    }

    protected Properties getRepositoryProperties(ServletContext ctx) throws IOException {
        Properties p = new Properties();
        p.load(ResourcesUtil.getResourceAsStream(reportSource, ctx));
        return p;
    }

    private void executeQuery(ServletContext ctx, ResultSetHandler rsh, String sql, String... params) throws SQLException {
        Connection conn = null;
        try {
            DbService dbService = DbService.getInstance(ctx);
            conn = dbService.getDataSource().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            if (params != null)
                for (int i = 1; i <= params.length; i++)
                    st.setString(i, params[i - 1]);
            ResultSet rs = st.executeQuery();
            rsh.handleResultSet(rs);
            rs.close();
            st.close();
        } finally {
            if (conn != null)
                conn.close();
        }

    }

    // inner classes: ----------------------------------------------------

    // handle Gson serialization of Maps
    private static class MyMap extends HashMap<String,String> {};

    // inner classes to handle resultset data extraction minimizing dupe code
    private abstract class ResultSetHandler {
        public abstract void handleResultSet(ResultSet rs) throws SQLException;
    }

    private class ResultSetHandlerString extends ResultSetHandler {

        protected int pos;
        protected String value;

        public ResultSetHandlerString(int pos) {
            this.pos = pos;
        }

        public String getString() {
            return value;
        }

        @Override
        public void handleResultSet(ResultSet rs) throws SQLException {
            if (rs.next())
                value = rs.getString(pos);
        }
    }

    private class ResultSetHandlerList extends ResultSetHandler {

        protected LinkedList<String> ret  = new LinkedList<>();
        protected int pos;

        public ResultSetHandlerList(int pos) {
            this.pos = pos;
        }

        @Override
        public void handleResultSet(ResultSet rs) throws SQLException {
            while (rs.next()) {
                ret.add(rs.getString(pos));
            }
        }

        public List<String> getList() throws SQLException {
            return ret;
        }
    }
}
