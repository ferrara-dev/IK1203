import controller.HTTPException;
import model.HTTPRequest;
import worker.RequestHandler;
import worker.RequestParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HTTPAsk implements Runnable {
    private final Socket socket;

    public HTTPAsk(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        String response  = "";
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            HTTPRequest request = RequestParser.readRequest(socket);
            response = RequestHandler.getResponse(request);

        } catch (HTTPException e) {
            response = RequestHandler.frameResponse(e.getCode(),e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            response = RequestHandler.frameResponse(200,"Could not connect to host\r\n");
            e.printStackTrace();
        }
        try {
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
