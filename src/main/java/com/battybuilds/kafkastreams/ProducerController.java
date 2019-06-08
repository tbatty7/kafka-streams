package com.battybuilds.kafkastreams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(MessageStreams.class)
public class ProducerController {

    @Autowired
    private MessageStreams messageChannels;

    @GetMapping("/send")
    public String sendMessage() {
        messageChannels.outputStream().send(MessageBuilder.withPayload("hello").build());
        System.out.println("Message Sent!");
        return "order_published";
    }

}
