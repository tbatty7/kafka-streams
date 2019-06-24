package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.avro.model.ClientIdentifier;
import org.junit.Test;

import java.util.Arrays;

public class SpecificRecordTests {

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

    

}
