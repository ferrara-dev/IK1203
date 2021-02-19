import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class HTTPEcho {

    public static void main(String[] args) throws IOException {
        if(args == null || args.length < 1)
            throw new IllegalArgumentException("First element of program argument must specify the servers port number");

        final byte [] HTTP_OK = "HTTP/1.1 200 OK\r\n\r\n".getBytes();
        final int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket clientConnection = serverSocket.accept();
                InputStream inputStream = clientConnection.getInputStream();
                StringBuilder serverResponse = new StringBuilder();
                int i = 0;
                int c = 0;
                Stack<Integer> stack = new Stack<>();
                for(int b = inputStream.read(); b != -1 && b!=0; b = inputStream.read(), i++){
                    String chr = new String(new byte[]{(byte) b});
                    serverResponse.append(chr);

                    if(i > 0) {
                        if(b == 13){
                            stack.push(b);
                            continue;
                        }

                        if(!stack.isEmpty())
                            if (b == 10 && stack.peek() == 13) {
                                c++;
                                if(c == 2)
                                    break;
                            } else {
                                stack.pop();
                                c = 0;
                            }
                    }

                }

                OutputStream outputStream = clientConnection.getOutputStream();
                outputStream.write(HTTP_OK);
                outputStream.write(serverResponse.toString().getBytes());
                clientConnection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

