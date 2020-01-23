package com.flightSimulatorApp.ex4;

import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class FlightSimulatorClient implements GetSetClient {

    private Socket socket;
    private PrintWriter writer;
    private int port;
    private String ip;

    public FlightSimulatorClient(String ip, int port) {
        this.port = port;
        this.ip = ip;
        socket = new Socket();

    }

    @Override
    public void connect() throws IOException, SocketException {
        socket.connect(new InetSocketAddress(ip, port), 3000);
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void disconnect() throws IOException{
        socket.close();
    }

    @Override
    public String get(String key) {
        return "";
    }

    @Override
    public void set(String key, String value) {
        String line = "set " + key + " " + value + "\r\n";
        writer.println(line);
    }
}
