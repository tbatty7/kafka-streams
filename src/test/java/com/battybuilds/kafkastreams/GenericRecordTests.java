package com.battybuilds.kafkastreams;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecordBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GenericRecordTests {

    @Test
    public void createGenericRecord() {
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
        GenericRecordBuilder builder = new GenericRecordBuilder(schema.getField("clientIdentifier").schema());
        GenericData.Record clientIdentifier = builder.set("hostName", "hostname")
                .set("ipAddress", "127.0.0.1")
                .build();

        GenericData.Record record = new GenericRecordBuilder(schema)
                .set("clientIdentifier", clientIdentifier)
                .set("employeeNames", Arrays.asList("Tim","Robert","Bill"))
                .set("requestTime", 10L)
                .build();
        System.out.printf("Full record\n");
        System.out.println(record.toString());
    }


}
