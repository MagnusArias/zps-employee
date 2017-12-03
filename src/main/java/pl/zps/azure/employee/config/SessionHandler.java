package pl.zps.azure.employee.config;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import pl.zps.azure.employee.model.Change;

import java.util.HashMap;
import java.util.Map;

public class SessionHandler {

    public Map<String, MessageChannel> clients;

    public SessionHandler() {
        this.clients = new HashMap<>();
    }

    public void addClient(StompHeaderAccessor client, MessageChannel messageChannel) {
        clients.put(client.getSessionId(), messageChannel);
    }

    public void removeClient(StompHeaderAccessor client) {
        clients.remove(client.getMessageId());
    }

    public void sendToAllClients(Change msg) {
        Map<String, Object> h = new HashMap<>();
        h.put("simpDestination", "/database/change");
        MessageHeaders headers = new MessageHeaders(h);
        for(MessageChannel clientChannel: clients.values()) {
            clientChannel.send(MessageBuilder.createMessage(msg, headers));
        }
        System.out.println("poszlo");
    }
}
