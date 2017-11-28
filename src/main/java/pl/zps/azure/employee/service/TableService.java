package pl.zps.azure.employee.service;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zps.azure.employee.model.TableMetadata;
import pl.zps.azure.employee.repository.TableMetadataRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    private final TableMetadataRepository tableMetadataRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TableService(TableMetadataRepository tableMetadataRepository) {
        this.tableMetadataRepository = tableMetadataRepository;
    }

    public List<TableMetadata> findAllTableMetada() {
        return tableMetadataRepository.findAll();
    }

    public void addTableName(TableMetadata tableMetadata) {
        tableMetadataRepository.save(tableMetadata);
    }

    public String findQuery(String query) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/employee_db";
        Connection conn = DriverManager.getConnection(url,"root","mysql");
        Statement stmt = conn.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery(query);
        String json = convertResultSetToJson(rs);

        conn.close();

        return json;
    }

    private String convertResultSetToJson(ResultSet resultSet) throws SQLException {
        Joiner commaJoiner = Joiner.on(", \n");

        StringBuilder builder = new StringBuilder();
        builder.append("{ \"results\": [ ");

        List<String> results = new ArrayList<String>();

        while (resultSet.next()) {
            List<String> resultBits = new ArrayList<String>();

            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                StringBuilder resultBit = new StringBuilder();
                String columnName = metaData.getColumnName(i);
                resultBit.append("\"").append(columnName).append("\": \"").append(resultSet.getString(i)).append("\"");
                resultBits.add(resultBit.toString());
            }

            results.add(" { " + commaJoiner.join(resultBits) + " } ");
        }

        builder.append(commaJoiner.join(results));
        builder.append("] }");
        return builder.toString();
    }

    public String getTableMetadata() throws SQLException {
        String table[] = { "TABLE" };
        ResultSet rs = null;
        ArrayList tables = null;

        String url = "jdbc:mysql://localhost:3306/employee_db";
        Connection conn = DriverManager.getConnection(url,"root","mysql");

        DatabaseMetaData metadata = conn.getMetaData();

        rs = metadata.getTables(null, null, "%", table);
        String json = convertResultSetToJson(rs);

        conn.close();

        return json;
    }
}
