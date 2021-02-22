package src.controller.handler;

import src.io.Buffer;
import src.model.HTTPRequestLine;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpHandler implements ConnectionHandler {
    private Socket socket;
    private Buffer buffer;

    public HttpHandler(Socket socket){
        this.socket = socket;
        this.buffer = new Buffer();
    }

    @Override
    public byte[] handle() {
        try {
            InputStream inputStream = socket.getInputStream();
            handleRequestLine(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    private HTTPRequestLine handleRequestLine(InputStream inputStream) throws IOException {
        int prev = -1;
        int curr = inputStream.read();
        int i = 0;

        while (curr != 0 && curr != -1){
            buffer.in(curr);
            if(curr == 10 && prev == 13) {
                break;
            }
            prev = curr;
            curr = inputStream.read();
            i++;
        }

        String headerLine = new String(buffer.getBuffer(),0,i);

        return null;
    }
}
