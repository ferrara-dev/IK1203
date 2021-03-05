package worker;

import controller.HTTPException;
import model.HTTPRequest;
import model.HTTPRequestLine;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequestParser {

    public static HTTPRequest readRequest(Socket socket) throws IOException, HTTPException {
        HTTPRequest httpRequest = new HTTPRequest();
        StringTokenizer inputBuffer = readInput(socket.getInputStream());
        HTTPRequestLine httpRequestLine = handleRequestLine(inputBuffer);
        httpRequest.setRequestLine(httpRequestLine);
        Map<String,String> headers = handleHeaders(inputBuffer);
        StringTokenizer body = null;
        if(headers.containsKey("Content-Length"))
            body = readInput(socket.getInputStream());
        httpRequest.setBody(body);
        return httpRequest;

    }

    private static StringTokenizer readInput(InputStream inputStream) throws IOException {
        int state = 0;
        boolean run = true;

        StringBuilder input = new StringBuilder();
        int i = 0;
        String line = "";
        byte[] buffer = new byte[512];

        while (!line.equals("\r\n")) {
            line = "";
            int count = 0;
            int currentByte = inputStream.read();
            int prevByte = 0;
            while (currentByte != 0 && currentByte != -1) {
                buffer[count] = (byte) currentByte;

                if (prevByte == 13 && currentByte == 10)
                    break;

                if (count > buffer.length / 2)
                    buffer = Arrays.copyOf(buffer, buffer.length * 2);

                prevByte = currentByte;
                currentByte = inputStream.read();
                count++;
            }

            line = new String(buffer, 0, count + 1, StandardCharsets.UTF_8);
            input.append(line);
        }

        return new StringTokenizer(input.toString(), "\r\n");
    }

    private static HTTPRequestLine handleRequestLine(StringTokenizer inputBuffer) throws HTTPException {
        if (!inputBuffer.hasMoreTokens())
            throw new HTTPException(400, "Invalid request line");

        StringTokenizer requestLine = new StringTokenizer(inputBuffer.nextToken(), " ");

        String method = "";
        try {
            method = requestLine.nextToken();
            if (!isValidMethod(method))
                throw new HTTPException(400, "Unrecognized HTTP method");
        } catch (NoSuchElementException e) {
            throw new HTTPException(400, "Invalid syntax : could not find request-uri in request line\r\n" + e.getMessage());
        }


        URI uri = null;
        Map<String,String> queryParams = null;
        String path = "";
        try {
            uri = new URI(requestLine.nextToken());
            String query = uri.getQuery();
            queryParams = decodeQueryString(query);
            path = uri.getPath();
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new HTTPException(400, "Bad request : \r\n" + e.getMessage());
        }

        String version = "";

        try {
            version = requestLine.nextToken();
            if (!isValidVersion(version))
                throw new HTTPException(400, "Invalid HTTP version");
        } catch (NoSuchElementException e) {
            throw new HTTPException(400, "HTTP version is missing");
        }

        HTTPRequestLine request =  new HTTPRequestLine(method,version,queryParams,path);

        return request;
    }

    private static Map<String,String> handleHeaders(StringTokenizer inputBuffer) throws HTTPException {
        Map<String,String> headerMap = new LinkedHashMap<>();
        while (inputBuffer.hasMoreTokens()){
            String header = inputBuffer.nextToken();
            String keyValuePair [] = header.split(":");
            headerMap.put(keyValuePair[0],keyValuePair[1]);
        }

        return headerMap;
    }

    private static boolean isValidVersion(String version) {
        if (version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))
            return true;
        else
            return false;
    }

    private static boolean isValidMethod(String method) {
        switch (method) {
            case "GET":
            case "HEAD":
            case "POST":
                return true;
            default:
                return false;
        }
    }

    private static Map<String, String> decodeQueryString(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new LinkedHashMap<>();
        if(Objects.isNull(query))
            return params;

        for (String param : query.split("&")) {
            String[] keyValue = param.split("=", 2);
            String key = URLDecoder.decode(keyValue[0], "UTF-8");
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
            if (!key.isEmpty()) {
                params.put(key, value);
            }
        }
        return params;
    }
}
