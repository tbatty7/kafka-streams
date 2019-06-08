package com.battybuilds.kafkastreams.utils;

import com.battybuilds.kafkastreams.utils.AvroSchemaBuilder;
import org.junit.Test;

public class AvroSchemaBuilderTest {

    @Test
    public void createAvroHttpRequestSchema() {
        AvroSchemaBuilder avroSchemaBuilder = new AvroSchemaBuilder();
        String schema = avroSchemaBuilder.createAvroHttpRequestSchema().toString();
        System.out.println(schema);
    }
}