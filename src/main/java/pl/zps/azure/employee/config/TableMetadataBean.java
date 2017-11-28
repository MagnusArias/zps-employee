package pl.zps.azure.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zps.azure.employee.model.TableMetadata;
import pl.zps.azure.employee.service.TableService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class TableMetadataBean {
    @Autowired
    private TableService tableService;

    @PostConstruct
    public void init() {
        List<TableMetadata> tableMetadata = createTableMetada();

        addTableMetadataIfNotExist(tableMetadata);
    }

    private List<TableMetadata> createTableMetada() {
        List<TableMetadata> metadataList = new ArrayList<>();

        metadataList.add(new TableMetadata("Employee", "id"));
        metadataList.add(new TableMetadata("Employee", "departmentId"));
        metadataList.add(new TableMetadata("Employee", "firstname"));
        metadataList.add(new TableMetadata("Employee", "lastname"));

        metadataList.add(new TableMetadata("Department", "id"));
        metadataList.add(new TableMetadata("Department", "name"));

        return metadataList;
    }

    private void addTableMetadataIfNotExist(List<TableMetadata> tableMetadata) {
/*
        List<TableMetadata> tableMetadataInDB = tableService.findAllTableMetada();

        for(int i = 0; i < tableMetadata.size(); i++) {
            TableMetadata tm = tableMetadata.get(i);
            for(int j = 0; j < tableMetadataInDB.size(); j++) {
                TableMetadata tmdb = tableMetadataInDB.get(j);
                if(tm.getTableName().equals(tmdb.getTableName()) &&
                        tm.getColumnName().equals(tmdb.getColumnName())) {
                    tableService.addTableName(tableMetadata.get(i));
                }
            }
        }
        */

        for(int i = 0; i < tableMetadata.size(); i++) {
            tableService.addTableName(tableMetadata.get(i));
        }
    }
}
