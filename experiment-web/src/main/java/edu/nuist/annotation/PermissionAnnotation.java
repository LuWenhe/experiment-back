package edu.nuist.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)    // 将注解放在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {

    String value() default "";

}
