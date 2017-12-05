package pl.zps.azure.employee.service;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zps.azure.employee.model.ColumnMetadata;
import pl.zps.azure.employee.model.TableMetadata;

import javax.persistence.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JDBCService {

    String hostName = "project-server.database.windows.net";
    String dbName = "db-project";

    private final String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=superuser;password=Haslo12345;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName);

    @Value("${jdbc.mysql.username}")
    private String username;

    @Value("${jdbc.mysql.password}")
    private String password;

    public String findQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery(query);
        String json = convertResultSetToJson(rs);

        conn.close();

        return json;
    }

    public List<TableMetadata> getTableMetadata() throws SQLException {
        Connection conn = getConnection();
        DatabaseMetaData metadata = conn.getMetaData();

        List<String> tableNames = getTableNamesMetadata(metadata);

        List<TableMetadata> tableMetadata = getTableWithColumnsMetadata(metadata, tableNames);

        conn.close();

        tableMetadata.remove(tableMetadata.size() - 1); //delete sys_config

        return tableMetadata;
    }

    private List<TableMetadata> getTableWithColumnsMetadata(DatabaseMetaData metadata, List<String> tableNames) throws SQLException {
        String   catalog           = null;
        String   schemaPattern     = null;
        String   columnNamePattern = "%";

        List<TableMetadata> tableMetadata = new ArrayList<>();

        for(int i = 0; i < tableNames.size(); i++) {
            ResultSet result = metadata.getColumns(
                    catalog, schemaPattern, tableNames.get(i), columnNamePattern);

            TableMetadata table = new TableMetadata(tableNames.get(i), new ArrayList<>());

            while(result.next()){
                String columnName = result.getString(4);
                int    columnType = result.getInt(5);

                table.getColumn().add(new ColumnMetadata(columnName, columnType));
            }

            tableMetadata.add(table);
        }

        return tableMetadata;
    }

    private List<String> getTableNamesMetadata(DatabaseMetaData metadataDB) throws SQLException {
        String   catalog          = null;
        String   schemaPattern    = null;
        String   tableNamePattern = "%";
        String[] types            = { "TABLE" };

        ResultSet result = metadataDB.getTables(
                catalog, schemaPattern, tableNamePattern, types );

        List<String> tables = new ArrayList<>();
        while(result.next()) {
            tables.add(result.getString(3));
        }

        return tables;
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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public boolean executeQuery(String statement) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();


        stmt.executeUpdate(statement);

        conn.close();

        return true;
    }
}
