package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import java.util.UUID;
import javax.jms.Destination;
import javax.jms.JMSException;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@RequiredArgsConstructor
@Component
public class HelloMessageListener
{
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, // Could omit this
                       Message message                  // Could omit this
    )
    {
        //System.out.println("I Got a Message!");

        //System.out.println(helloWorldMessage);
        //throw new RuntimeException();
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, // Could omit this
                       Message message                  // Could omit this
    ) throws JMSException
    {
        HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
                                                 .id(UUID.randomUUID())
                                                 .message("World!")
                                                 .build();
        jmsTemplate.convertAndSend((Destination)message.getJMSReplyTo(), payloadMsg);
        // throw new RuntimeException();
    }

}
