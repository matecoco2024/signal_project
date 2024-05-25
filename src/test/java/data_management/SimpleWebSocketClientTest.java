package data_management;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.SimpleWebSocketClient;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimpleWebSocketClientTest {

    private DataStorage mockDataStorage;
    private SimpleWebSocketClient simpleWebSocketClient;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws URISyntaxException {
        System.setOut(new PrintStream(outContent));

        mockDataStorage = mock(DataStorage.class);
        simpleWebSocketClient = new SimpleWebSocketClient(new URI("ws://localhost:8080"));
        simpleWebSocketClient.connectAndReadData(mockDataStorage);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testOnMessageValidData() {
        String message = "1,1627890123456,HeartRate,72.5";
        simpleWebSocketClient.onMessage(message);

        verify(mockDataStorage, times(1)).addPatientData(1, 72.5, "HeartRate", 1627890123456L);
    }

    @Test
    void testOnMessageInvalidData() {
        String invalidMessage = "invalid,data";
        simpleWebSocketClient.onMessage(invalidMessage);

        verify(mockDataStorage, never()).addPatientData(anyInt(), anyDouble(), anyString(), anyLong());
        assertTrue(outContent.toString().contains("Error processing message: invalid,data"));
    }

    @Test
    void testOnOpen() {
        ServerHandshake handshake = mock(ServerHandshake.class);
        simpleWebSocketClient.onOpen(handshake);
        assertTrue(outContent.toString().trim().contains("Connected to WebSocket server"));
    }

    @Test
    void testOnClose() {
        simpleWebSocketClient.onClose(1000, "Normal closure", true);
        assertTrue(outContent.toString().trim().contains("Disconnected from WebSocket server"));
    }

    @Test
    void testOnError() {
        Exception exception = new Exception("Test exception");
        simpleWebSocketClient.onError(exception);
        assertTrue(outContent.toString().contains("Test exception"));
    }
}
