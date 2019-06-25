package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.MessageStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

@EnableBinding(MessageStreams.class)
public class Consumer {

    private final AvroSerDes serDes;

    public Consumer(AvroSerDes serDes) {
        this.serDes = serDes;
    }

    @StreamListener(MessageStreams.STREAM_ONE)
    public void streamHandler(byte[] messageRequest, @Headers MessageHeaders headers) {
        AvroHttpRequest request = serDes.deserializeBinary(messageRequest);
        System.out.println("****************MESSAGE RECEIVED BELOW***************************");
        System.out.println(new String(messageRequest));
        DataStore.setValue(request.toString());
        System.out.println(request.toString());
        System.out.println("Message Headers\n" + headers);
        System.out.println("---------EntrySet:  " + headers.entrySet());
        System.out.println("--------Consumer:  " + headers.get("kafka_consumer"));
    }
}
