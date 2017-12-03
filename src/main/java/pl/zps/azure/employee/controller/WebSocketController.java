package pl.zps.azure.employee.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zps.azure.employee.model.Change;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {

    @MessageMapping("/event")
    @SendTo("/database/change")
    public Change send(Change change) throws Exception {
        return change;
    }
}
