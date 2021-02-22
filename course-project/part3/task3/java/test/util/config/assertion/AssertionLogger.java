package util.config.assertion;

public class AssertionLogger {
    private static volatile AssertionLogger instance;

    public static AssertionLogger getInstance() {
        if (instance == null) {
            synchronized (AssertionLogger .class) {
                if (instance == null) {
                    instance = new AssertionLogger();
                }
            }
        }
        return instance;
    }
}
