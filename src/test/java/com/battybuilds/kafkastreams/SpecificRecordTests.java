package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.avro.model.ClientIdentifier;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.RequestUtils;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.record.RecordsUtil;
import org.junit.Test;

import java.util.Arrays;

public class SpecificRecordTests {

    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void createSpecificRecord() {
        ClientIdentifier clientIdentifier = ClientIdentifier.newBuilder()
                .setIpAddress("122.2.22.222")
                .setHostName("welcome to my party")
                .build();
        AvroHttpRequest request = AvroHttpRequest.newBuilder()
                .setClientIdentifier(clientIdentifier)
                .setEmployeeNames(Arrays.asList("Tim", "Bobby", "Bill"))
                .setRequestTime(54L)
                .build();
        System.out.println(request);
    }

    @Test
    public void specificRecordCanBeDeserializedAsGenericRecord() {
        AvroHttpRequest record = RequestUtils.createAvroHttpRequest();
        byte[] serializedRecord = avroSerDes.serializeBinary(record);
        GenericRecord deserializedRecord = avroSerDes.deserializeBinaryToGeneric(serializedRecord);
        System.out.println("Deserialized as GenericRecord\n" + deserializedRecord);
    }
}
