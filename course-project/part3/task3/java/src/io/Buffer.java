package src.io;

import src.util.ArrayUtil;

public class Buffer {
    private byte [] buffer = new byte[1024];
    int bufferedBytes = 0;

    public Buffer(){

    }

    public void in(int...bytes){
        for(int i = 0; i < bytes.length; i++){
            if (bufferedBytes >= buffer.length/2)
                buffer = ArrayUtil.resize(buffer.length * 2, buffer);
            buffer[bufferedBytes] = (byte) bytes[i];
            bufferedBytes++;
        }
    }

    public void in(byte...bytes){
        for(Byte b : bytes){
            in(b);
        }
    }

    public void in(Byte b){
        in(b.byteValue());
    }

    public void in(byte b){
        if (bufferedBytes >= buffer.length/2)
            buffer = ArrayUtil.resize(buffer.length * 2, buffer);
        buffer[bufferedBytes] = b;
        bufferedBytes++;
    }

    public byte [] getBuffer(){
        return buffer;
    }
}
