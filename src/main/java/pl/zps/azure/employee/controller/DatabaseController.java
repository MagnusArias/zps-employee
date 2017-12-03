package pl.zps.azure.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zps.azure.employee.config.SessionHandler;
import pl.zps.azure.employee.model.Change;
import pl.zps.azure.employee.model.TableMetadata;
import pl.zps.azure.employee.service.JDBCService;
import pl.zps.azure.employee.service.TableService;

import java.sql.*;
import java.util.List;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    final TableService tableService;

    final JDBCService jdbcService;

    final
    SessionHandler sessionHandler;

    @Autowired
    public DatabaseController(TableService tableService, JDBCService jdbcService, SessionHandler sessionHandler) {
        this.tableService = tableService;
        this.jdbcService = jdbcService;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping("/table/list")
    List<TableMetadata> getTableList() throws SQLException {
        return jdbcService.getTableMetadata();
    }

    @GetMapping("/query/{query}")
    String getQueryList(@PathVariable("query") String query) throws SQLException {
        return jdbcService.findQuery(query);
    }

    @PostMapping("/execute/update")
    boolean updateQuery(@RequestBody String statement) throws SQLException {
        return jdbcService.executeQuery(statement);
    }

    @PostMapping("/execute/delete")
    boolean deleteQuery(@RequestBody String statement) throws SQLException {
        return jdbcService.executeQuery(statement);
    }

    @PostMapping("/execute/insert")
    boolean insertQuery(@RequestBody String statement) throws SQLException {
        return jdbcService.executeQuery(statement);
    }
}
