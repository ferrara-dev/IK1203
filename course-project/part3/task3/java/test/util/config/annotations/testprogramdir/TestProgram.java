package util.config.annotations.testprogramdir;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)  //on class level
public @interface TestProgram {

    int priority() default 2;

    String[] params() default "";


}
