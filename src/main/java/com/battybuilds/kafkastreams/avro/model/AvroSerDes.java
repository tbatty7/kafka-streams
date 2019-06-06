package com.battybuilds.kafkastreams.avro.model;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

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
}
