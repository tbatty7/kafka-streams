package com.battybuilds.kafkastreams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(MessageStreams.class)
public class ProducerController {

    private final MessageStreams messageChannels;

    public ProducerController(MessageStreams messageChannels) {
        this.messageChannels = messageChannels;
    }

    @GetMapping("/send")
    public String sendMessage() {

        String messageString = "hello";
        messageChannels.outputStream().send(MessageBuilder.withPayload(messageString).build());
        System.out.println("Message Sent!");
        return "order_published";
    }

}
