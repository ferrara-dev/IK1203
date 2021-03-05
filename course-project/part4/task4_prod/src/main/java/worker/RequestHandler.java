package worker;

import controller.HTTPException;
import model.HTTPRequest;
import service.TCPClient;

import java.io.IOException;
import java.net.Socket;
import java.net.http.HttpResponse;
import java.util.*;

public class RequestHandler {


    public static String getResponse(HTTPRequest httpRequest) throws IOException {
        try {
            String path = httpRequest.getRequestLine().getPath();
            String method = httpRequest.getRequestLine().getMethod();
            Map<String, String> queryParameters = httpRequest.getRequestLine().getQueryParameters();

            switch (path) {
                case "/ask":
                    if (method.equals("GET"))
                        return frameResponse(200,handleAsk(queryParameters));
                    else
                        throw new HTTPException(405, String.format("%s not implemented for resource %s", method, path));
                default:
                    throw new HTTPException(404, "path : " + path + " not found");
            }
        } catch (HTTPException e){
            return frameResponse(e.getCode(),e.getMessage());
        }
    }

    public static String frameResponse(int statusCode, String body){
        switch (statusCode){
            case 200:
                return String.format("HTTP/1.1 200 OK\r\n\r\n%s\r\n",body);
            case 400:
                return String.format("HTTP/1.1 400 Bad Request\r\n\r\n%s\r\n",body);
            case 404:
                return String.format("HTTP/1.1 404 Not Found\r\n\r\n%s\r\n",body);
            case 405:
                return String.format("HTTP/1.1 405 Method Not Allowed\r\n\r\n%s\r\n",body);
        }
        return null;
    }

    private static String handleAsk(Map<String,String> queryParams) throws HTTPException, IOException {
        validateParams(queryParams,"hostname","port");
        int port = 0;
        try {
            port = Integer.parseInt(queryParams.get("port"));
        } catch (NumberFormatException numberFormatException){
            throw new HTTPException(400,"Could not parse query param \"port\"\r\n" + queryParams.get("port")+ " is not an integer");
        }
        String hostname = queryParams.get("hostname");
        String string = queryParams.get("string");

        if(Objects.isNull(string))
          return TCPClient.askServer(hostname,port);
        else
           return TCPClient.askServer(hostname,port,string);
    }


    private static void validateParams(Map<String,String> params, String...names) throws HTTPException {
        String sb = "";
        for(String name : names){
            if(!params.containsKey(name)){
                sb = sb + " " + name;
            }
        }
        if(!sb.equals("")){
            String err = "expected query parameters : ";
            StringTokenizer st = new StringTokenizer(sb, " ");
            for(String s : params.keySet()){
                err += " <" + s + "> ";
            }
            err += "\r\n";
            err += "got query parameters : ";
            while (st.hasMoreTokens()){
                err += " <" + st.nextToken() + "> ";
            }
            throw new HTTPException(400,err);
        }
    }
}

