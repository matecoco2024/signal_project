package com.data_management;

import java.io.IOException;

public interface DataReader {
    /**
     * Connects to a specified data source and starts receiving data.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException;
}
