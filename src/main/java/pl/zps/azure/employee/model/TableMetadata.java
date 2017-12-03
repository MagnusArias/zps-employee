package pl.zps.azure.employee.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableMetadata {

    String name;

    List<ColumnMetadata> column;

}
