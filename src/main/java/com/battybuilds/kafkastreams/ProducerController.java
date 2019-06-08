package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(MessageStreams.class)
public class ProducerController {

    private final MessageStreams messageChannels;
    private AvroSerDes serDes;

    public ProducerController(MessageStreams messageChannels, AvroSerDes serDes) {
        this.messageChannels = messageChannels;
        this.serDes = serDes;
    }

    @GetMapping("/send")
    public String sendMessage() {

        AvroHttpRequest request = null;
        byte[] serializedRequest = serDes.serealizeAvroHttpRequestBinary(request);
        messageChannels.outputStream().send(MessageBuilder.withPayload(serializedRequest).build());
        System.out.println("Message Sent!");
        return "order_published";
    }

}
