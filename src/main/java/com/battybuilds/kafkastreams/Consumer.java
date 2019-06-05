package com.battybuilds.kafkastreams;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageStreams.class)
public class Consumer {

    @StreamListener(MessageStreams.STREAM_ONE)
    public void streamHandler(String log) {
        System.out.println(log);
    }
}
