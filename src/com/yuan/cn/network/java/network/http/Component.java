package com.yuan.cn.network.java.network.http;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE})
@Documented
public @interface Component {
    String value() default "";

}
