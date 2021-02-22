package util.config.assertion;

import util.config.exception.TestException;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Assertions {
    private AssertionLogger logger = AssertionLogger.getInstance();

    public static boolean equals(Object expected, Object actual){
        if(Objects.equals(expected, actual))
            return true;
        else
            throw new TestException(String.format("Reason : Compared objects are not equal\r\n" +
                    "Expected : %s\r\nActual : %s\r\n",expected.toString(), actual.toString()));
    }


}
