package com.battybuilds.kafkastreams.utils;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class AvroSerDes {

    public byte[] serializeJson(AvroHttpRequest request) {
        DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(AvroHttpRequest.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get()
                    .jsonEncoder(AvroHttpRequest.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (Exception e) {
            System.out.println("Serialization Error: " + e.getMessage());
        }

        return data;
    }


    public AvroHttpRequest deserializeJson(byte[] message) {
        DatumReader<AvroHttpRequest> reader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder;
        try {
            decoder = DecoderFactory.get()
                    .jsonDecoder(AvroHttpRequest.getClassSchema(), new String(message));
            return reader.read(null, decoder);
        } catch (Exception e) {
            System.out.println("Deserialization error: " + e.getMessage());
            return null;
        }
    }

    public byte[] serializeBinary(AvroHttpRequest request) {
        DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(AvroHttpRequest.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = EncoderFactory.get()
                .binaryEncoder(stream, null);
        try {
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    public AvroHttpRequest deserializeBinary(byte[] data) {
        DatumReader<AvroHttpRequest> specificReader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder = DecoderFactory.get()
                .binaryDecoder(data, null);
        try {
            return specificReader.read(null, decoder);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public GenericRecord deserializeBinaryToGeneric(byte[] serializedRecord) {
        Schema schema = getSchema();

        DatumReader<GenericRecord> genericReader = new GenericDatumReader<>(schema);
        BinaryDecoder decoder = DecoderFactory.get()
                .binaryDecoder(serializedRecord, null);
        try {
            return genericReader.read(null, decoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public byte[] serializeBinaryFromGeneric(GenericRecord genericRecord) {
        Schema schema = getSchema();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = EncoderFactory.get()
                .binaryEncoder(stream, null);
        try {
            writer.write(genericRecord, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
}
