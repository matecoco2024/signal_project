package data_management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.PatientRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDataReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadData() throws IOException {
        // Create a test file
        Path testFile = tempDir.resolve("test.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(testFile)) {
            writer.write("Patient ID: 1, Timestamp: 1714376789050, Label: WhiteBloodCells, Data: 100.0\n");
            writer.write("Patient ID: 1, Timestamp: 1714376789051, Label: WhiteBloodCells, Data: 200.0\n");
        }

        // Read data from the test file
        FileDataReader reader = new FileDataReader(testFile.toString());
        DataStorage storage = new DataStorage();
        reader.readData(storage);

        // Verify the data
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(200.0, records.get(1).getMeasurementValue());
    }
}