package com.battybuilds.kafkastreams;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageStreams.class)
public class Consumer {

    @StreamListener(MessageStreams.STREAM_ONE)
    public void streamHandler(byte[] log) {
        System.out.println(log);
        System.out.println(log.length);
    }
}
