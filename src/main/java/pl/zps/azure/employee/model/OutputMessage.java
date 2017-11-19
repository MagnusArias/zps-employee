package pl.zps.azure.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class OutputMessage {
    String from;
    String text;
    String time;
}
