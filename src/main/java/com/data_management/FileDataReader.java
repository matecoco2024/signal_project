package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads data from a file and stores it in the data storage.
 */

public class FileDataReader implements DataReader {
    
    private String filePath;

    /**
     * Constructs a new instance of FileDataReader with the specified file path.
     * 
     * @param filePath the path to the file containing the data
     */

    public FileDataReader(String filePath) {
        this.filePath = filePath;
    } 

    /**
     * Reads data from the file and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length != 4) {
                    throw new IOException("Invalid data format");
                }
                int patientId = Integer.parseInt(parts[0].split(": ")[1]);
                long timestamp = Long.parseLong(parts[1].split(": ")[1]);
                String recordType = parts[2].split(": ")[1];
                double measurementValue = Double.parseDouble(parts[3].split(": ")[1]);
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FileDataReader <filePath>");
            System.exit(1);
        }
    
        String filePath = args[0];
        FileDataReader reader = new FileDataReader(filePath);
    
        // Create a DataStorage instance
        DataStorage dataStorage = new DataStorage();
    
        try {
            reader.readData(dataStorage);
            System.out.println("Data read successfully from " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading data from " + filePath);
            e.printStackTrace();
        }
    }
}