package com.battybuilds.kafkastreams.utils;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import org.apache.avro.Schema;
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

    // Is this the correct message Converter? - No, need dependency
//    public MessageConverter myMessageConverter() {
//        return new AvroSchemaMessageConverter(MimeType.valueOf("avro/bytes"));
//    }

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

    public byte[] serializeBinaryFromSpecific(AvroHttpRequest specificRecord) {
        DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(AvroHttpRequest.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder binaryEncoder = EncoderFactory.get()
                .binaryEncoder(stream, null);
        try {
            writer.write(specificRecord, binaryEncoder);
            binaryEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    public AvroHttpRequest deserializeBinaryToSpecific(byte[] data) {
        DatumReader<AvroHttpRequest> specificReader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder = DecoderFactory.get()
                .binaryDecoder(data, null);
        try {
            AvroHttpRequest avroHttpRequest = specificReader.read(null, decoder);
            return avroHttpRequest;
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
            GenericRecord genericRecord = genericReader.read(null, decoder);
            return genericRecord;
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
        Encoder binaryEncoder = EncoderFactory.get()
                .binaryEncoder(stream, null);
        try {
            writer.write(genericRecord, binaryEncoder);
            binaryEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
}
