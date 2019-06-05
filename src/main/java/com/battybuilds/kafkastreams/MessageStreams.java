package com.battybuilds.kafkastreams;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface MessageStreams {

     String STREAM_ONE = "streamOne";

     @Input
     MessageChannel streamOne();
}
