package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.RequestUtils;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GenericRecordTests {

    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void createGenericRecord() {
        Schema schema = getSchema();
        GenericRecord record = createGenericRecord(schema);
        System.out.println("Full record");
        System.out.println(record.toString());
    }

    @Test
    public void canDeserializeGenericRecordAsSpecificRecord() {
        Schema schema = getSchema();
        GenericRecord genericRecord = createGenericRecord(schema);
        byte[] serializedGenericRecord = avroSerDes.serializeBinaryFromGeneric(genericRecord);
        AvroHttpRequest deserializedRecord = avroSerDes.deserializeBinary(serializedGenericRecord);
        System.out.println(deserializedRecord);
    }

    private GenericRecord createGenericRecord(Schema schema) {
        GenericRecordBuilder builder = new GenericRecordBuilder(schema.getField("clientIdentifier").schema());
        GenericRecord clientIdentifier = builder.set("hostName", "hostname")
                .set("ipAddress", "127.0.0.1")
                .build();

        return new GenericRecordBuilder(schema)
                .set("clientIdentifier", clientIdentifier)
                .set("employeeNames", Arrays.asList("Tim", "Robert", "Bill"))
                .set("requestTime", 10L)
                .build();
    }

    @Nullable
    private Schema getSchema() {
        Schema.Parser parser = new Schema.Parser();
        Schema schema = null;
        try {
            schema = parser.parse(new File("src/main/resources/avroHttpRequest-schema.avsc"));
            System.out.println("Schema");
            System.out.println(schema.toString());
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return schema;
    }


}
