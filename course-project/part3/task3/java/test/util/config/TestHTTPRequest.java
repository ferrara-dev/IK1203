package util.config;


import util.config.annotations.testdir.Test;
import util.config.annotations.testprogramdir.TestProgram;
import util.config.assertion.Assertions;

@TestProgram(priority = 1, params = {"a","b","c"})
public class TestHTTPRequest {
    @Test
    public void test(){
        Assertions.equals(2, 3);
    }


}
