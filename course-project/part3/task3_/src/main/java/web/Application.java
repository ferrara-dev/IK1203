package web;

import web.ServerProcess;

import java.io.IOException;
import java.net.ServerSocket;

public class Application {

    public static void main(String...args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            ServerProcess serverProcess = new ServerProcess(serverSocket.accept());
            Thread clientThread = new Thread(serverProcess);
            clientThread.start();
        }
    };

}
