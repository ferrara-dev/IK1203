package src.controller;

import src.controller.handler.ConnectionHandler;
import src.controller.handler.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher {
    private static volatile Dispatcher instance;
    private int port;

    public Dispatcher(int port){
        this.port = port;
    }

    public void run(){
        while (true){
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                new Thread(() -> {
                    try {
                        Socket inComingConnection = serverSocket.accept();
                        HttpHandler httpHandler = new HttpHandler(inComingConnection);
                        httpHandler.handle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
