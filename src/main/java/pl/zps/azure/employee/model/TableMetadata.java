package pl.zps.azure.employee.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(TableMetadata.TableMetadataKey.class)
@Table(name = "table_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableMetadata {
    @Column(name = "table_name")
    @Id
    String tableName;

    @Column(name = "column_name")
    @Id
    String columnName;


    public static class TableMetadataKey implements Serializable {
        String columnName;
        String tableName;
    }


}
