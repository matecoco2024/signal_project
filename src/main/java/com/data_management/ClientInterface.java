package com.data_management;

import java.io.IOException;

public interface ClientInterface {
    void connect(String serverUri) throws IOException;
    void disconnect() throws IOException;
}