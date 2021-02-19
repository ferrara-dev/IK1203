package app.tcpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class TCPClient {


    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        Socket clientSocket = new Socket(hostname,port); // start connection to server

        OutputStream outputStream = clientSocket.getOutputStream(); // write message to server
        outputStream.write(ToServer.getBytes());
        StringBuilder serverResponse = new StringBuilder();

        try {
            clientSocket.setSoTimeout(3000); // set timeout to 3 sec and get server input stream
            InputStream inputStream = clientSocket.getInputStream();

            // read bytes from server input stream one by one
            for(int b = inputStream.read(); b != -1 && b!= 0; b = inputStream.read()){
                serverResponse.append(new String(new byte[]{(byte) b}));
            }

        } catch (SocketTimeoutException se) {
            System.err.println(se.getMessage());
        }

        clientSocket.close();
        return serverResponse.toString();
    }

    public static String askServer(String hostname, int port) throws IOException {
        Socket clientSocket = new Socket(hostname,port); // start connection to server

        StringBuilder serverResponse = new StringBuilder();
        try {
            clientSocket.setSoTimeout(3000); // set timeout to 3 sec and get server input stream
            InputStream inputStream = clientSocket.getInputStream();

            // read bytes from server input stream one by one, if SocketTimeOutException is thrown during read, data read so far is returned
            for(int b = inputStream.read(); b != -1 && b!= 0; b = inputStream.read()){
                serverResponse.append(new String(new byte[]{(byte) b}));
            }

        } catch (SocketTimeoutException se) {
            System.err.println(se.getMessage());
        }

        clientSocket.close();
        return serverResponse.toString();
    }
}
