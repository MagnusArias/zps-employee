package pl.zps.azure.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String from;
    private String text;

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }
}
