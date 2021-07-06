package com.whh.mylibrary.annotation.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作业练习，利用反射+注解获取getIntent中的参数值
 * date : 2021-06-22
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowire {
    String value() default "";
}
