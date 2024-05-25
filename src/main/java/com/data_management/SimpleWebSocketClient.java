package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SimpleWebSocketClient extends WebSocketClient {

    private DataStorage dataStorage;

    public SimpleWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public void connectAndReadData(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        try {
            // Process the incoming message and store it in dataStorage
            String[] parts = message.split(",");
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            double data = Double.parseDouble(parts[3]);

            // Use the addPatientData method to store the data
            dataStorage.addPatientData(patientId, data, label, timestamp);
        } catch (Exception e) {
            System.err.println("Error processing message: " + message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        try {
            String serverUri = "ws://localhost:8080";
            SimpleWebSocketClient client = new SimpleWebSocketClient(new URI(serverUri));
            DataStorage dataStorage = new DataStorage(); // Replace with actual implementation
            client.connectAndReadData(dataStorage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
