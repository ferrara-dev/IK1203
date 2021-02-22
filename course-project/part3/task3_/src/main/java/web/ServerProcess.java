package web;

import com.sun.net.httpserver.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerProcess implements Runnable {
    private Socket socket;

    private StringBuilder inputBuilder;
    private byte [] inputBuffer;

    public ServerProcess(Socket socket){
        this.socket = socket;
        inputBuffer = new byte[1024];
    }

    public void run(){

        try {
            InputStream inputStream = socket.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            int cnt = 0;
            byte readByte = (byte) inputStream.read();
            byte prevByte = (byte) 0;

            while (readByte != (byte) 0 && readByte != (byte) -1){
                resizeBuffer(cnt);
                inputBuffer[cnt] = readByte;

                if(readByte == 13 && prevByte == 10){
                    String line = new String(inputBuffer,0,cnt);
                    inputBuffer = new byte[cnt];
                    cnt = 0;
                    String [] fractions = line.split(" ");
                    if(fractions[0].equals("GET")){

                    } else {

                    }
                }

                cnt++;
                prevByte = readByte;
                readByte = (byte) inputStream.read();
            }

        } catch (IOException e) {

        }
    }


    /**
     * Double the size of the input buffer if
     * @param n current amount of elements
     */
    private void resizeBuffer(int n){
        // if buffer size is to small, double the size
        if(n + 1 == inputBuffer.length){
            int newLen = inputBuffer.length*2;
            byte [] tempBuffer = new byte[newLen];
            for(int j = 0; j < inputBuffer.length; j++){
                tempBuffer[j] = inputBuffer[j];
            }
            inputBuffer = tempBuffer;
        }
    };
}
/*
       while (readByte != (byte) 0 && readByte != (byte) -1){
                resizeBuffer(cnt);
                inputBuffer[cnt] = readByte;

                if(step == 0) {
                    if(readByte == (byte) 13)
                        step = 1;
                    else
                        flag = 0;
                }

                else if(step == 1){
                    if(readByte == (byte) 10){
                        step = 0;
                        flag++;
                        if(flag == 2)
                            break;
                    } else {
                        flag = 0;
                    }
                }
                cnt++;
                readByte = (byte) inputStream.read();
            }
 */