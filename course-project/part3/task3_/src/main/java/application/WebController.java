package application;


public class WebController{

    /**
     *
     */
    private static volatile WebController instance;

    private WebController(){

    }

    public static WebController getInstance() {
        if (instance == null) {
            synchronized (WebController.class) {
                if (instance == null) {
                    instance = new WebController();
                }
            }
        }
        return instance;
    };

    public void askServer(){

    }

}
