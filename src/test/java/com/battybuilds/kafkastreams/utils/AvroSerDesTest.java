package com.battybuilds.kafkastreams.utils;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import org.junit.Test;

import static com.battybuilds.kafkastreams.utils.RequestUtils.createAvroHttpRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AvroSerDesTest {


    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void canSerializeRequestToJsonByteArray() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] bytes = avroSerDes.serializeJson(request);
        String expectedJSON = "{\"requestTime\":10,\"clientIdentifier\":{\"hostName\":\"hostName\",\"ipAddress\":\"127.0.0.1\"},\"employeeNames\":[\"Tim\",\"Jessica\",\"Mike\"]}";
        assertEquals(expectedJSON, new String(bytes));
    }

    @Test
    public void canSerializeRequestToBinaryByteArray() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] serializedRequest = avroSerDes.serializeBinaryFromSpecific(request);
        String result = new String(serializedRequest);
        assertTrue(result.contains("127.0.0.1"));
        assertTrue(result.contains("Tim"));
    }

    @Test
    public void canDeserializeAvroRequest() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] serializedMessage = avroSerDes.serializeJson(request);
        AvroHttpRequest deserializedRequest = avroSerDes.deserializeJson(serializedMessage);
        assertEquals(request, deserializedRequest);
        assertEquals(hostName(request), hostName(deserializedRequest).toString());
        System.out.println(deserializedRequest.toString());
    }

    @Test
    public void canDeserializeFromBinary() {
        AvroHttpRequest request = createAvroHttpRequest();
        byte[] serializedMessage = avroSerDes.serializeBinaryFromSpecific(request);
        AvroHttpRequest deserializedRequest = avroSerDes.deserializeBinaryToSpecific(serializedMessage);
        assertEquals(request, deserializedRequest);
    }

    private CharSequence hostName(AvroHttpRequest request) {
        return request.getClientIdentifier().getHostName();
    }


}
