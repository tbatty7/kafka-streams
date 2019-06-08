package com.battybuilds.kafkastreams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(MessageStreams.class)
public class Producer {

    @Autowired
    private MessageStreams source;

    @GetMapping("/send")
    public String sendMessage() {
        source.outputStream().send(MessageBuilder.withPayload("hello").build());
        System.out.println("Message Sent!");
        return "order_published";
    }

//    @InboundChannelAdapter(channel = MessageStreams.OUTPUT_STREAM, poller = @Poller(fixedRate = "5000"))
//    public Message<?> generate(String payload) {
//        // Might need other import for MessageBuilder
//        return MessageBuilder.withPayload(payload)
//                .build();
//    }

}
