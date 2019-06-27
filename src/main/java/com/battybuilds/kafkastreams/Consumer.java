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
        AvroHttpRequest request = serDes.deserializeBinaryToSpecific(messageRequest);


        System.out.println("****************MESSAGE RECEIVED BELOW***************************");
        System.out.println(new String(messageRequest));
        DataStore.setValue(request.toString());
        System.out.println(request.toString());
        System.out.println("Message Headers\n" + headers);
        System.out.println("---------EntrySet:  " + headers.entrySet());
        System.out.println("--------kafka_offset:  " + headers.get("kafka_offset"));
        System.out.println("--------scst_nativeHeadersPresent:  " + headers.get("scst_nativeHeadersPresent"));
        System.out.println("--------Consumer:  " + headers.get("kafka_consumer"));
        System.out.println("--------deliveryAttempt:  " + headers.get("deliveryAttempt"));
        System.out.println("--------kafka_timestampType:  " + headers.get("kafka_timestampType"));
        System.out.println("--------kafka_receivedMessageKey:  " + headers.get("kafka_receivedMessageKey"));
        System.out.println("--------kafka_receivedPartitionId:  " + headers.get("kafka_receivedPartitionId"));
        System.out.println("--------contentType:  " + headers.get("contentType"));
        System.out.println("--------kafka_receivedTopic:  " + headers.get("kafka_receivedTopic"));
        System.out.println("--------kafka_receivedTimestamp:  " + headers.get("kafka_receivedTimestamp"));
        System.out.println("--------ErrorChannel: " + headers.getErrorChannel());
        System.out.println("--------Id: " + headers.getId());
        System.out.println("--------ReplyChannel: " + headers.getReplyChannel());
        System.out.println("--------TimeStamp: " + headers.getTimestamp());
        System.out.println("--------KeySet: " + headers.keySet());
    }
}
