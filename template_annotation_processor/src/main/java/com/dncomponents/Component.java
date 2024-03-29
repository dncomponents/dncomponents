package com.dncomponents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
public @interface Component {
    String template() default "";

    String tag() default "";
}
