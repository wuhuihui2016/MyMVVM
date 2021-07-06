package com.whh.mylibrary.annotation.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : wuhuihui
 * date : 2021-06-21
 * desc : 运行时通过反射自动注入View，不再需要写findViewById
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectView {
    int id() default -1;
}
