package com.whh.others.genericity;

import java.util.Arrays;

/**
 * 反射的bug：小心泛型擦除
 * getMethods():当前类和父类的所有public方法
 * getDeclaredMethods:获得当前类所有的public、protected、package和private方法
 *
 * 类型擦除：
 *     泛型基本上都是在编译器这个层次上实现的，在生成的字节码中是不包含泛型中的类型信息的，使用泛型的时候加上类型参数，
 *     在编译器编译的时候会去掉，这个过程成为类型擦除。
 *     如在代码中定义List<Object>和List<String>等类型，在编译后都会变成List，JVM看到的只是List，而由泛型附加的类型信息对JVM是看不到的
 *
 * @param <T>
 */
public class GenericityBug<T> {

    private void level(int level) {
        System.out.println("int level " + level);
    }

    private void level(Integer level) {
        System.out.println("Integer level " + level);
    }

    public static void main(String[] args) {
        GenericityBug genericityBug = new GenericityBug();
        try {
            genericityBug.getClass().getDeclaredMethod("level", Integer.class)
                    .invoke(genericityBug, Integer.valueOf(666)); //TODO　Integer level 666

            genericityBug.getClass().getDeclaredMethod("level", Integer.TYPE)
                    .invoke(genericityBug, Integer.valueOf(666)); //TODO　int level 666

            // TODO 使用反射调用方法时，参数类型为 Integer.TYPE 指代的是 int 类型；Integer.class 指代的是 Integer 类型

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=======dividingLine getMethods========");

        Sub1 sub1 = new Sub1();
        Sub1 finalSub = sub1;
        Arrays.stream(sub1.getClass().getMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .forEach(method -> {
                    try {
                        method.invoke(finalSub, "JavaEdge");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(sub1.toString());

        /* getMethods():当前类和父类的所有public方法
        由于子类 sub1 的 setValue 不是重写的父类的方法，
        此时父类 getMethods 找到了两个 setValue 方法(一个是本身的方法，一个是继承自父类的方法)，
        导致两次调用Base.setValue。
        called Sub1.setValue
        called Base.setValue
        called Base.setValue
        value：JavaEdge updataCount：2 */

        System.out.println("=======dividingLine getDeclaredMethods========");
        sub1 = new Sub1();
        Sub1 finalSub1 = sub1;
        Arrays.stream(sub1.getClass().getDeclaredMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .forEach(method -> {
                    try {
                        method.invoke(finalSub1, "JavaEdge");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(sub1.toString());

        /* getDeclaredMethods:获得当前类所有的public、protected、package和private方法
        不太能理解啊？？？？
        called Sub1.setValue
        called Base.setValue
        value：JavaEdge updataCount：1 */

        System.out.println("=======dividingLine Sub2========");

        // Sub2 指明了调用类型!!!
        Sub2 sub2 = new Sub2();
        Arrays.stream(sub2.getClass().getDeclaredMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .findFirst().ifPresent(method -> {
            try {
                method.invoke(sub2, "JavaEdge");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(sub2.toString());

        /*
        called Sub2.setValue
        called Base.setValue
        value：JavaEdge updataCount：1 */

    }

}
