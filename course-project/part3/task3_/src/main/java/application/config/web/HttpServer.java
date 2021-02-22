package application.config.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpServer {
    int port() default 8080;

    String hostname() default "localhost";

    /**
     * Http methods accepted by this web server
     */
    String [] method() default {

    };
}
