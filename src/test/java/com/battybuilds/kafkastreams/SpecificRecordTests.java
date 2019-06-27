package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.avro.model.ClientIdentifier;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.RequestUtils;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.junit.Test;

import java.util.Arrays;

public class SpecificRecordTests {

    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void createSpecificRecord() {
        ClientIdentifier clientIdentifier = ClientIdentifier.newBuilder()
                .setIpAddress("127.0.0.1")
                .setHostName("welcome to my party")
                .build();

        AvroHttpRequest request = AvroHttpRequest.newBuilder()
                .setClientIdentifier(clientIdentifier)
                .setEmployeeNames(Arrays.asList("Tim", "Robert", "Bill"))
                .setRequestTime(10L)
                .build();
        System.out.println(request);
    }

    @Test
    public void canDeserializeSpecificRecordAsGenericRecord() {
        AvroHttpRequest record = RequestUtils.createAvroHttpRequest();
        byte[] serializedRecord = avroSerDes.serializeBinaryFromSpecific(record);
        GenericRecord deserializedRecord = avroSerDes.deserializeBinaryToGeneric(serializedRecord);
        System.out.println("Deserialized as GenericRecord\n" + deserializedRecord);
    }

    @Test
    public void getSchemaFromSpecificRecord() {
        AvroHttpRequest record = RequestUtils.createAvroHttpRequest();
        Schema schema = record.getSchema();
        System.out.println(schema);
    }
}
