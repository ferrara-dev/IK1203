package worker;


import controller.Controller;

import java.util.HashMap;

public class Mapper {

    public static Object getController(String path){
        switch (path){
            case "/ask":
                return new Controller();
            default:
                return null;
        }

    }


}
