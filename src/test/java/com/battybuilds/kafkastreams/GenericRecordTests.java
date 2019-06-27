package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GenericRecordTests {

    private AvroSerDes avroSerDes = new AvroSerDes();

    @Test
    public void canCreateGenericRecord() {
        Schema schema = getMySchema();
        GenericRecord record = createGenericRecord(schema);
        System.out.println("Full record");
        System.out.println(record.toString());
    }

    @Test
    public void canDeserializeGenericRecordAsSpecificRecord() {
        Schema schema = getMySchema();
        GenericRecord genericRecord = createGenericRecord(schema);
        byte[] serializedGenericRecord = avroSerDes.serializeBinaryFromGeneric(genericRecord);
        AvroHttpRequest deserializedRecord = avroSerDes.deserializeBinaryToSpecific(serializedGenericRecord);
        System.out.println(deserializedRecord);
    }

    @Test
    public void getSchemaFromGenericRecord() {
        Schema schema = getMySchema();
        GenericRecord genericRecord = createGenericRecord(schema);
        Schema obtainedSchema = genericRecord.getSchema();
        System.out.println("Schema from Record\n" + obtainedSchema);
        assertEquals(schema, obtainedSchema);
    }

    private GenericRecord createGenericRecord(Schema schema) {
        GenericRecordBuilder builder = new GenericRecordBuilder(schema.getField("clientIdentifier").schema());
        GenericRecord clientIdentifier = builder
                .set("ipAddress", "127.0.0.1")
                .set("hostName", "welcome to my party")
                .build();

        GenericData.Record build = new GenericRecordBuilder(schema)
                .set("clientIdentifier", clientIdentifier)
                .set("employeeNames", Arrays.asList("Tim", "Robert", "Bill"))
                .set("requestTime", 10L)
                .build();
        return build;
    }

    @Nullable
    private Schema getMySchema() {
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
