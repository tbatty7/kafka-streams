package com.battybuilds.kafkastreams.avro.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AvroSerDesTest {
    @Test
    public void canSerializeRequestToByteArray() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] bytes = serialize(request);
        assertEquals(141, bytes.length);
    }

    private byte[] serialize(AvroHttpRequest request) {
        AvroSerDes avroSerDes = new AvroSerDes();
        return avroSerDes.serealizeAvroHttpRequestJSON(request);
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
                .setActive(Active.YES)
                .build();
    }
}