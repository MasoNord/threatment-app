package org.project;

import com.sun.net.httpserver.HttpServer;
import org.project.app.ServerController;
import org.project.app.ServerService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    private static final int serverPort = 8000;
    private final static ServerController serverController = new ServerController();
    public static void main(String[] args) throws IOException {
        serverController.Handler(Main.serverPort);
        System.out.println("Server listening on port 8000");
    }
}
