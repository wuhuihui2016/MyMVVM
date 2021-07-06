package com.whh.mymvvm.utils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.whh.mylibrary.annotation.annot.InjectView;
import com.whh.mylibrary.annotation.annot.Autowire;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * date : 2021-06-21
 * desc : 用来解析注解InjectView
 */
public class InjectViewUtil {

    /**
     * 解析注解InjectView
     * 通过反射，找到Activity中使用了@InjectView的字段，然后通过findViewById来初始化控件。
     *
     * @param activity 使用InjectView的目标对象
     */
    public static void inject(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Field[] fields = cls.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true); //设置所有字段可访问，否则即使反射，也不能访问private字段
        for (Field field : fields) {
            boolean needInject = field.isAnnotationPresent(InjectView.class); //该字段或者方法是否被InjectView注解
            if (needInject) {
                InjectView anno = field.getAnnotation(InjectView.class);
                int id = anno.id();
                if (id == -1) continue;
                View view = activity.findViewById(id);
                Class fieldType = field.getType();
                try {
                    field.set(activity, fieldType.cast(view)); //反射赋值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过apt生成的代码找到控件
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        Class cls = activity.getClass();
        try {
            Class<?> bindClass = Class.forName(cls.getCanonicalName() + "_ViewBinding");//找到生成的相应的bind类工具
            Method method = bindClass.getMethod("bindView", cls);
            method.invoke(bindClass.newInstance(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过注解+反射获取Intent的参数值
     * https://blog.csdn.net/fl2502923/article/details/116017192
     *
     * @param activity
     */
    public static void injectIntentParam(Activity activity) {
        try {
            //获取数据
            if (activity.getIntent() == null) return;
            Bundle bundle = activity.getIntent().getExtras();
            if (bundle == null) return;

            Class<? extends Activity> cls = activity.getClass();
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Autowire.class)) {
                    Autowire autowire = field.getAnnotation(Autowire.class);
                    String key = TextUtils.isEmpty(autowire.value()) ? field.getName() : autowire.value();
                    if (bundle.containsKey(key)) {
                        Object value = bundle.get(key);
                        field.set(activity, value); //反射赋值
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //================获取含有多个注解的方法中注解及注解参数 20210625================//
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AnnoMethod1 {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AnnoMethod2 {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER) //作用于参数的注解
    public @interface AnnoMethod3 {
        String key() default "";

        String value() default "";
    }

    @AnnoMethod1(value = "AnnoMethod1")
    @AnnoMethod2(value = "AnnoMethod2")
    public static void annoMethod(@AnnoMethod3(key = "33") String a, @AnnoMethod3(value = "333") String b) {
        System.out.println("annoMethod..." + a + "  " + b);
    }

    /**
     * 遍历方法上的所有注解及其参数
     */
    public static void ergodicAnno() {
        try {
            Class cls = Class.forName("com.whh.mymvvm.utils.InjectViewUtil");
            //如果方法有参，获取需加参数类型，否则报错：java.lang.NoSuchMethodException
            Method method = cls.getDeclaredMethod("annoMethod", String.class, String.class);
            Annotation[] annotations = method.getAnnotations();//获取方法上的所有注解
            for (int i = 0; i < annotations.length; i++) {
                System.out.println("ergodicAnno...annotation=" + annotations[i]);
            }

            Annotation[][] parameterAnnotations = method.getParameterAnnotations(); //获取方法上所有注解的参数
            System.out.println("ergodicAnno...parameterAnnotations.length=" + parameterAnnotations.length);
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    System.out.println("ergodicAnno...parameter=" + parameterAnnotations[i][j]);
                }
            }
        } catch (Exception e) {
            System.out.println("ergodicAnno...Exception=" + e.toString());
        }
    }

    //================获取含有多个注解的方法中注解及注解参数 20210625================//
}
