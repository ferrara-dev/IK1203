package util.config;


import util.config.ClassAnnotationProcessor;
import util.config.TestHTTPRequest;

public class TestConfig {

    public static void main(String[] args) throws Exception {
        ClassAnnotationProcessor processor = new ClassAnnotationProcessor();
        Class<TestHTTPRequest> obj = TestHTTPRequest.class;
        processor.process(obj);


    }
}
