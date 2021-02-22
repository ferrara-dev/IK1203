import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class HTTPEcho {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        final byte[] HTTP_OK = "HTTP/1.1 200 OK\r\n\r\n".getBytes();
        int port = 8080;
        try {
            if (args == null || args.length > 0)
                port = Integer.parseInt(args[0]);

            serverSocket = new ServerSocket(port);

            System.out.printf("\033[0;3m HTTP Echo server started successfully\033[0m \r\n");

            while (true) { // Start server process
                Socket clientConnection = serverSocket.accept(); // Listen and accept any incoming TCP connections
                System.out.printf("\033[0;32m a new client connected succesfully\033[0m \r\n");
                InputStream inputStream = clientConnection.getInputStream();
                System.out.printf("\t>\033[0;32m handling request...\033[0m \r\n");
                byte[] inputBuffer = new byte[1024];
                int cnt = 0;
                int step = 0;
                int flag = 0;
                try {
                    int currentByte = inputStream.read();
                    // read input while connection is open and eof is not reached, also stops if any null byte is read
                    while (currentByte != -1 && currentByte != 0) {
                        inputBuffer[cnt] = (byte) currentByte;

                        // if buffer size is to small, double the size
                        if (cnt + 1 == inputBuffer.length) {
                            int newLen = inputBuffer.length * 2;
                            byte[] tempBuffer = new byte[newLen];
                            for (int j = 0; j < inputBuffer.length; j++) {
                                tempBuffer[j] = inputBuffer[j];
                            }
                            inputBuffer = tempBuffer;
                        }

                        if (step == 0) {
                            if (currentByte == (byte) 13)
                                step = 1;
                            else
                                flag = 0;
                        } else if (step == 1) {
                            if (currentByte == (byte) 10) {
                                step = 0;
                                flag++;
                                if (flag == 2)
                                    break;
                            } else {
                                flag = 0;
                            }
                        }
                        cnt++;
                        currentByte = inputStream.read();
                    }
                    String response = new String(inputBuffer, 0, cnt, StandardCharsets.UTF_8);
                    OutputStream outputStream = clientConnection.getOutputStream();
                    outputStream.write(HTTP_OK);
                    outputStream.write(response.getBytes());
                    clientConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e){
            System.err.println("\033[0;31m Something went wrong when starting webserver from HTTPEcho.main\033[0m \r\n");
            e.printStackTrace();
        }

    }

    public static byte[] patternMatch(InputStream stream) throws IOException {
        int a = stream.read();

        return null;
    }
}

