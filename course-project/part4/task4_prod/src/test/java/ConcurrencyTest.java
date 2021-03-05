import controller.HTTPException;
import model.HTTPRequest;
import service.TCPClient;
import worker.RequestHandler;
import worker.RequestParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConcurrencyTest implements Runnable {
    private Socket socket;

    public ConcurrencyTest(Socket socket) {
        this.socket = socket;
    }

    public static Thread start(){
        Thread t = new Thread(new ServerStarter());
        t.start();
        return t;
    }

    public static void main(String... args) {
        //start server
        Thread t0 = start();

        String server1Message = "/ask?hostname=time-a-g.nist.gov&port=13 HTTP/1.1\r\n\r\n";

        startThread("/ask?hostname=time-a-g.nist.gov&port=13 HTTP/1.1\r\n");
        startThread("/ask?hostname=time-e-g.nist.gov&port=13 HTTP/1.1\r\n");
        startThread("/ask?hostname=time-e-g.nist.gov&port=13 HTTP/1.1\r\n");
        startThread("/ask?hostname=time-e-g.nist.gov&port=13 HTTP/1.1\r\n");
    }

    public static void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){

        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null;
        String response = "";
        try {
            outputStream = socket.getOutputStream();
            HTTPRequest request = RequestParser.readRequest(socket);
            response = RequestHandler.getResponse(request);

        } catch (HTTPException e) {
            response = RequestHandler.frameResponse(e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            response = RequestHandler.frameResponse(200, "Could not connect to host\r\n");
            e.printStackTrace();
        }
        try {
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startThread(String s) {
        new Thread(() -> {
            try {
                String s1;
                s1 = TCPClient.askServer("localhost",
                        8080,
                        "GET " +
                                s +
                                "Host:asdasd\r\n\r\n");
                System.out.println(s1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static class ServerStarter implements Runnable{
        private ServerStarter(){

        }

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                while (true){
                    Thread t = new Thread(new ConcurrencyTest(serverSocket.accept()));
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
