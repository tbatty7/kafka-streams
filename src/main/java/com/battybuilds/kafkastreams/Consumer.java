package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.MessageStreams;
import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageStreams.class)
public class Consumer {

    AvroSerDes serDes = new AvroSerDes();

    @StreamListener(MessageStreams.STREAM_ONE)
    public void streamHandler(byte[] messageRequest) {
        AvroHttpRequest request = serDes.deserializeBinary(messageRequest);
        System.out.println("****************MESSAGE RECEIVED BELOW***************************");
        System.out.println(new String(messageRequest));
        System.out.println(request.toString());
    }
}
