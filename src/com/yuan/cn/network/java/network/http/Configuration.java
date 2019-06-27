package com.yuan.cn.network.java.network.http;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Component
public @interface Configuration {

    String[] basePackage() default {};
    Class<?>[] baseClasses() default {};

}
