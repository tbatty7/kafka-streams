package com.battybuilds.kafkastreams;

public class DataStore {
    public static String getValue() {
        return value;
    }

    public static void setValue(String value) {
        DataStore.value = value;
    }

    private static String value;


}
