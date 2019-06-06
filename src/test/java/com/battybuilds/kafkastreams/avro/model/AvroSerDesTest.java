package com.battybuilds.kafkastreams.avro.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AvroSerDesTest {


    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void canSerializeRequestToByteArray() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] bytes = avroSerDes.serializeAvroHttpRequest(request);
        assertEquals(141, bytes.length);
    }

    @Test
    public void canDeserializeAvroRequest() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] serializedMessage = avroSerDes.serializeAvroHttpRequest(request);
        AvroHttpRequest deserializedRequest = avroSerDes.deserializeAvroHttpRequest(serializedMessage);
        assertEquals(request, deserializedRequest);
        assertEquals(hostName(request), hostName(deserializedRequest).toString());
    }

    private CharSequence hostName(AvroHttpRequest request) {
        return request.getClientIdentifier().getHostName();
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