package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.MessageStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageStreams.class)
public class Consumer {

    private final AvroSerDes serDes;

    public Consumer(AvroSerDes serDes) {
        this.serDes = serDes;
    }

    @StreamListener(MessageStreams.STREAM_ONE)
    public void streamHandler(byte[] messageRequest) {
        AvroHttpRequest request = serDes.deserializeBinary(messageRequest);
        System.out.println("****************MESSAGE RECEIVED BELOW***************************");
        System.out.println(new String(messageRequest));
        DataStore.setValue(request.toString());
        System.out.println(request.toString());
    }
}
