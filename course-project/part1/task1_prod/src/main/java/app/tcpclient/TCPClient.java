package app.tcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;


public class TCPClient {


    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        Socket clientSocket = new Socket(hostname,port); // start connection to server

        OutputStream outputStream = clientSocket.getOutputStream(); // write message to server
        outputStream.write(ToServer.getBytes(StandardCharsets.UTF_8));
        byte [] inputBuffer = new byte[1024];
        int count = 0;
        try {
            clientSocket.setSoTimeout(3000); // set timeout to 3 sec and get server input stream
            InputStream inputStream = clientSocket.getInputStream();
            // read bytes from server input stream one by one
            for(int b = inputStream.read(); b != -1 && b!= 0; b = inputStream.read()){
                // if buffer size is to small, double the size
                if(count + 1 == inputBuffer.length){
                    int newLen = inputBuffer.length*2;
                    byte [] tempBuffer = new byte[newLen];
                    for(int j = 0; j < inputBuffer.length; j++){
                        tempBuffer[j] = inputBuffer[j];
                    }
                    inputBuffer = tempBuffer;
                }
                inputBuffer[count] = (byte) b;
                count++;
            }
        } catch (SocketTimeoutException se) {
            System.err.println(se.getMessage());
        }

        clientSocket.close();
        return new String(inputBuffer,0,count,StandardCharsets.UTF_8);
    }

    public static String askServer(String hostname, int port) throws IOException {
        Socket clientSocket = new Socket(hostname,port); // start connection to server

        byte [] inputBuffer = new byte[1024];
        int count = 0;
        try {
            clientSocket.setSoTimeout(3000); // set timeout to 3 sec and get server input stream
            InputStream inputStream = clientSocket.getInputStream();


            // read bytes from server input stream one by one, if SocketTimeOutException is thrown during read, data read so far is returned
            // read bytes from server input stream one by one
            for(int b = inputStream.read(); b != -1 && b!= 0; b = inputStream.read()){
                // if buffer size is to small, double the size
                if(count + 1 == inputBuffer.length){
                    int newLen = inputBuffer.length*2;
                    byte [] tempBuffer = new byte[newLen];
                    for(int j = 0; j < inputBuffer.length; j++){
                        tempBuffer[j] = inputBuffer[j];
                    }
                    inputBuffer = tempBuffer;
                }
                inputBuffer[count] = (byte) b;
                count++;
            }

        } catch (SocketTimeoutException se) {
            System.err.println(se.getMessage());
        }

        clientSocket.close();
        return new String(inputBuffer,0,count,StandardCharsets.UTF_8);
    }
}
