package com.flightSimulatorApp.ex4;

import java.io.IOException;
import java.net.SocketException;

public interface GetSetClient {

    public void connect() throws IOException, SocketException;
    public void disconnect() throws IOException;

    // get and set throught socket.
    public String get(String key);
    public void set(String key, String value);
}
