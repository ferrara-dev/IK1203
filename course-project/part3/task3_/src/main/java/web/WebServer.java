package web;

import application.config.web.HttpServer;
import java.lang.annotation.Annotation;
@HttpServer(port=8888, method = {"GET"})
public class WebServer {

    public static void main(String...args){
        Class<WebServer> obj = WebServer.class;

        if(obj.isAnnotationPresent(HttpServer.class)) {
            Annotation annotation = obj.getAnnotation(HttpServer.class);
            HttpServer httpServer = (HttpServer) annotation;
            System.out.println("port : " + httpServer.port());
            System.out.print("hostname : " + httpServer.hostname());
            System.out.print("");
        }
    }
}
