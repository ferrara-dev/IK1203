package controller;

public class HTTPException extends Exception{
    int code;
    public  HTTPException(int statusCode, String msg){
        super(msg);
        code = statusCode;
    }

    public int getCode() {
        return code;
    }
}
