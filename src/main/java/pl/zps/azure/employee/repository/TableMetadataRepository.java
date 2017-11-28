package pl.zps.azure.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zps.azure.employee.model.TableMetadata;

public interface TableMetadataRepository extends JpaRepository<TableMetadata, TableMetadata.TableMetadataKey>
{
    TableMetadata findByTableName(String tableName);
}
