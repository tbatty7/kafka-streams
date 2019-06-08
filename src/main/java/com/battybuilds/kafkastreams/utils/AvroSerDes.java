package com.battybuilds.kafkastreams.utils;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class AvroSerDes {

    public byte[] serializeAvroHttpRequest(AvroHttpRequest request) {
        DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(AvroHttpRequest.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder(AvroHttpRequest.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (Exception e) {
            System.out.println("Serialization Error: " + e.getMessage());
        }

        return data;
    }


    public AvroHttpRequest deserializeAvroHttpRequest(byte[] message) {
        DatumReader<AvroHttpRequest> reader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder;
        try {
            decoder = DecoderFactory.get().jsonDecoder(AvroHttpRequest.getClassSchema(), new String(message));
            return reader.read(null, decoder);
        } catch (Exception e) {
            System.out.println("Deserialization error: " + e.getMessage());
            return null;
        }
    }

    public byte[] serealizeAvroHttpRequestBinary(AvroHttpRequest request) {
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
        DatumReader<AvroHttpRequest> employeeReader = new SpecificDatumReader<>(AvroHttpRequest.class);
        Decoder decoder = DecoderFactory.get()
                .binaryDecoder(data, null);
        try {
            return employeeReader.read(null, decoder);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
