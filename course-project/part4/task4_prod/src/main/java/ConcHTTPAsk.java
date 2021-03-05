import controller.HTTPException;
import model.HTTPRequest;
import worker.Mapper;
import worker.RequestHandler;
import worker.RequestParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcHTTPAsk {

    public static void main(String...args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true){
            Socket socket = serverSocket.accept();
            Thread t = new Thread(new HTTPAsk(socket));
            t.start();
        }
    }
}
