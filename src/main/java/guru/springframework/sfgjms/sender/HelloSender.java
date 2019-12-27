package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HelloSender
{
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage()
    {
        HelloWorldMessage message = HelloWorldMessage.builder()
                                                     .id(UUID.randomUUID())
                                                     .message("Hello world!")
                                                     .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }


    @Scheduled(fixedRate = 2000)
    public void sendAndRecieveMessage() throws JMSException
    {
        System.out.println("I'm sending a message");

        HelloWorldMessage message = HelloWorldMessage.builder()
                                                     .id(UUID.randomUUID())
                                                     .message("Hello world!")
                                                     .build();

        Message receiveMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException
            {
                Message helloMessage;
                try{
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

                    System.out.println("Sending Hello");

                    return helloMessage;
                }
                catch (JsonProcessingException e)
                {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println(receiveMsg.getBody(String.class));
    }

}
