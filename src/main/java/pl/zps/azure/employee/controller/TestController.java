package pl.zps.azure.employee.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.zps.azure.employee.model.Message;
import pl.zps.azure.employee.model.OutputMessage;
import pl.zps.azure.employee.model.TestMessage;
import pl.zps.azure.employee.repository.TestDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class TestController {

    @Autowired
    private TestDao testDao;

    @PersistenceContext
    private EntityManager entityManager;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

    @GetMapping("/test")
    public @ResponseBody
    TestMessage testMessage() {
        TestMessage testMessage = new TestMessage("Panie, wszystko ladnie dziala!");

        testDao.save(testMessage);

        return testMessage;
    }

    @GetMapping("/test/all")
    public @ResponseBody
    List<TestMessage> testMessages() {
        return testDao.findAll();
    }

    @GetMapping("/query/{query}")
    public @ResponseBody
    List<TestMessage> getQuery(@PathVariable("query") String query) {
        List<TestMessage> result = (List<TestMessage>) entityManager
                .createQuery("from TestMessage as tm WHERE tm.id = :id") //query
                .setParameter("id", Long.parseLong(query))
                .getResultList();

        return result;
    }
}
