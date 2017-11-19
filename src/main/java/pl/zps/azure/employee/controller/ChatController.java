package pl.zps.azure.employee.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.zps.azure.employee.model.Message;
import pl.zps.azure.employee.model.OutputMessage;
import pl.zps.azure.employee.model.TestMessage;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

    @GetMapping("/test")
    public @ResponseBody
    TestMessage testMessage() {
        return new TestMessage("Panie, wszystko ladnie dziala!");
    }
}
