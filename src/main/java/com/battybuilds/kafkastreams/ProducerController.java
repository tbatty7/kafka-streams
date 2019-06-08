package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.avro.model.ClientIdentifier;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.MessageStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@EnableBinding(MessageStreams.class)
public class ProducerController {

    private final MessageChannel outputChannel;
    private AvroSerDes serDes;

    public ProducerController(MessageChannel outputStream, AvroSerDes serDes) {
        this.outputChannel = outputStream;
        this.serDes = serDes;
    }

    @GetMapping("/send")
    public String sendMessage() {
        AvroHttpRequest request = createAvroHttpRequest();

        byte[] serializedRequest = serDes.serializeBinary(request);
        outputChannel.send(MessageBuilder.withPayload(serializedRequest).build());
        System.out.println("Message Sent!");
        return "message_published";
    }

    private AvroHttpRequest createAvroHttpRequest() {
        ClientIdentifier clientIdentifier = ClientIdentifier.newBuilder()
                .setHostName("hostName")
                .setIpAddress("127.0.0.1")
                .build();

        return AvroHttpRequest.newBuilder()
                .setClientIdentifier(clientIdentifier)
                .setEmployeeNames(Arrays.asList("Tim", "Jessica", "Mike"))
                .setRequestTime(10L)
                .build();
    }

}
