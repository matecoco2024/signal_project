package data_management;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class DataStorageTest {

    
    private DataStorage dataStorage;


    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
    }

    @Test
    void testAddAndGetRecords() {
        // DataReader reader
        dataStorage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        dataStorage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = dataStorage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }   

    @Test
    void testAddPatientData() {
        dataStorage.addPatientData(1, 72.5, "HeartRate", 1627890123456L);
        List<PatientRecord> records = dataStorage.getRecords(1, 1627890123000L, 1627890123460L);
        assertEquals(1, records.size());
        assertEquals(72.5, records.get(0).getMeasurementValue());
    }

    @Test
    void testConcurrentUpdates() throws InterruptedException {
        int numThreads = 100;
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            new Thread(() -> {
                dataStorage.addPatientData(1, 72.5, "HeartRate", System.currentTimeMillis());
                latch.countDown();
            }).start();
        }

        latch.await(5, TimeUnit.SECONDS);
        List<PatientRecord> records = dataStorage.getAllPatients().get(0).getAllRecords();
        assertEquals(numThreads, records.size());
    }
}
