package es.jdl.sqlcrud.services.rest;

import es.jdl.sqlcrud.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllTablesDefServlet extends CRUDServiceServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. get config
        // 2. check permission
        // 3. get data
        JsonUtil.respondWithObject(resp, dbService.getAllTables());
    }
}
