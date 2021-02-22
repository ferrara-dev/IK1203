package application.service;

import java.io.IOException;

public class HttpService {
    private static volatile HttpService instance;

    private HttpService() throws IOException {

    }

    public static HttpService getInstance() throws IOException {
        if (instance == null) {
            synchronized (HttpService.class) {
                if (instance == null) {
                    instance = new HttpService();
                }
            }
        }
        return instance;
    };

    public boolean validateMessage(){
        return false;
    }
}
