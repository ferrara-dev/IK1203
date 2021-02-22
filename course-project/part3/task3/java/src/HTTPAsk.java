package src;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HTTPAsk {
    public static void main( String[] args) {
        try{
            int port = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            runServer(serverSocket);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void runServer(ServerSocket serverSocket){
        while (true){
            try {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte [] inputBuffer = readData(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static byte [] readData(InputStream inputStream) throws IOException {
        byte [] inputBuffer = new byte[1024];

        int i = 0;

        for (byte b = (byte) inputStream.read(); b != (byte) -1; b =(byte) inputStream.read()){
            if(i == inputBuffer.length){
                i = 0;
            }
            inputBuffer[i] = b;

        }
        return inputBuffer;
    }
}

