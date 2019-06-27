package com.battybuilds.kafkastreams.utils;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;

public interface MessageStreams {

     String STREAM_ONE = "streamOne";
     String OUTPUT_STREAM = "outputStream";

     @Input
     MessageChannel streamOne();

     @Output(OUTPUT_STREAM)
     @Profile("test")
     MessageChannel outputStream();
}
