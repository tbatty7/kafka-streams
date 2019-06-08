package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.AvroSerDes;
import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.avro.model.ClientIdentifier;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AvroSerDesTest {


    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void canSerializeRequestToByteArray() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] bytes = avroSerDes.serializeAvroHttpRequest(request);
        String expectedJSON = "{\"requestTime\":10,\"clientIdentifier\":{\"hostName\":\"hostName\",\"ipAddress\":\"127.0.0.1\"},\"employeeNames\":[\"Tim\",\"Jessica\",\"Mike\"]}";
        assertEquals(expectedJSON, new String(bytes));
    }

    @Test
    public void canDeserializeAvroRequest() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] serializedMessage = avroSerDes.serializeAvroHttpRequest(request);
        AvroHttpRequest deserializedRequest = avroSerDes.deserializeAvroHttpRequest(serializedMessage);
        assertEquals(request, deserializedRequest);
        assertEquals(hostName(request), hostName(deserializedRequest).toString());
        System.out.println(deserializedRequest.toString());
    }

    @Test
    public void canDeserializeFromBinary() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] serializedMessage = avroSerDes.serealizeAvroHttpRequestBinary(request);
        AvroHttpRequest deserializedRequest = avroSerDes.deserializeBinary(serializedMessage);
        assertEquals(request, deserializedRequest);
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
                .build();
    }
}