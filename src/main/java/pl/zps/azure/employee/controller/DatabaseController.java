package pl.zps.azure.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zps.azure.employee.model.TableMetadata;
import pl.zps.azure.employee.service.TableService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    final TableService tableService;

    @Autowired
    public DatabaseController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/table/list")
    List<TableMetadata> getTableList() {
        return tableService.findAllTableMetada();
    }

    @GetMapping("/query/{query}")
    String getQueryList(@PathVariable("query") String query) throws SQLException {
        return tableService.findQuery(query);
    }

    @GetMapping("/table/metadata")
    String getTableMetadata() throws SQLException {
        return tableService.getTableMetadata();
    }
}
