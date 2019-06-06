package com.battybuilds.kafkastreams;

import org.junit.Test;

import static org.junit.Assert.*;

public class AvroSchemaBuilderTest {

    @Test
    public void createAvroHttpRequestSchema() {
        AvroSchemaBuilder avroSchemaBuilder = new AvroSchemaBuilder();
        String schema = avroSchemaBuilder.createAvroHttpRequestSchema().toString();
        System.out.println(schema);
    }
}