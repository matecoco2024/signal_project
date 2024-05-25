package com;

import java.io.IOException;
import java.net.URISyntaxException;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;
import com.data_management.SimpleWebSocketClient;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else {
            HealthDataSimulator.main(new String[]{});
        }
    }
}