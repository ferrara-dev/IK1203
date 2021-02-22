package util.config;


import util.config.annotations.testdir.Test;
import util.config.annotations.testprogramdir.TestProgram;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Scanner;

public class ClassAnnotationProcessor {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE


    public Object process(Class c) {
        if (c.isAnnotationPresent(TestProgram.class)){
            int passed = 0, failed = 0, count = 0, ignore = 0;

            Annotation annotation = c.getAnnotation(TestProgram.class);
            TestProgram testerInfo = (TestProgram) annotation;

            StringBuilder testLog = new StringBuilder();
            testLog.append(String.format("Tested class : %s\r\n", c.getSimpleName()));

            // Process @Test
            for (Method method : c.getDeclaredMethods()) {
                // if method is annotated with @Test
                if (method.isAnnotationPresent(Test.class)) {
                    Annotation methodAnnotation = method.getAnnotation(Test.class);
                    Test test = (Test) methodAnnotation;

                    // if enabled = true (default)
                    if (test.enabled()) {
                        System.out.println();
                        String msg = String.format("Tested method : %s", method.getName());
                        testLog.append(msg);
                        testLog.append("\r\n");
                        try {
                            try {
                                method.invoke(c.getDeclaredConstructor().newInstance());
                                testLog.append("Result : Passed\r\n");
                            } catch (Throwable ex) {
                                StringWriter sw = new StringWriter();
                                PrintWriter printWriter = new PrintWriter(sw);
                                ex.printStackTrace(printWriter);


                                testLog.append(colored(Color.RED_BOLD,"Result : Failed\r\n"));
                                testLog.append(colored(Color.RED,sw.toString()));
                                failed++;
                            }
                        }
                        catch (AssertionError assertionError){
                            testLog.append(assertionError.getMessage());
                        }

                        System.out.println(testLog.toString());

                    } else {
                        System.out.printf("%s - Test '%s' - ignored%n", ++count, method.getName());
                        ignore++;
                    }

                }
                return true;
            }
        }
        return false;
    }


    private String colored(Color color, String string){
        return new StringBuilder()
                .append(color.code)
                .append(string)
                .append(Color.RESET)
                .toString();
    }

    private String tabs(String s, int tabs){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tabs; i++)
                sb.append("\t");
        sb.append(s);
        return sb.toString();
    }

    public enum Color{
        RED("\033[0;31m"),
        RED_BOLD("\033[1;31m"),
        RESET("\033[0m");
        public String code;

        Color(String s) {
            code = s;
        }
    }
}
