package com.whh.others;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 序列化：将对象转化为二进制，用于保存，或者网络传输。
 * 反序列化：与序列化相反，将二进制转化成对象。
 * 序列化ID是为了保证成功进行反序列化，如果未指定，将自动生成
 * (只有同一次编译生成的class才会生成相同的serialVersionUID)
 * 如果出现需求变动，Bean类发生改变，则会导致反序列化失败。为了不出现这类的问题，所以最好显式指定serialVersionUID。
 *
 * 使用transient修饰，让某个变量不被序列化。
 * 序列化对象的引用类型成员变量，也必须是可序列化的，否则，会报错。
 * 反序列化时必须有序列化对象的class文件。
 * 当通过文件、网络来读取序列化后的对象时，必须按照实际写入的顺序读取。
 * 单例类序列化，需要重写readResolve()方法；否则会破坏单例原则。
 * 同一对象序列化多次，只有第一次序列化为二进制流，以后都只是保存序列化编号，不会重复序列化。
 * 建议所有可序列化的类加上serialVersionUID 版本号，方便项目升级。
 *
 * author:wuhuihui 2021.07.07
 */
public class SerializablePerson implements Serializable {

    //不需要序列化姓名和年龄，在序列化写入后，读取到的字段为空或0或false
    //SerializablePerson{name='null', age=0, job='programer'}
    transient String name;
    transient int age;
    String job;

    public SerializablePerson(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    /**
     * 序列化写入文本
     */
    //C 盘拒绝访问！(java.io.FileNotFoundException: C:\whhh0707.txt (拒绝访问。))
    static String fileName = "D:\\whhh0707.txt";

    public static void writeObject() {
        try {

            readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("write Exception==>" + e.toString());
        }
    }

    /**
     * 读取序列化文本转换成bean
     */
    public static void readObject() {
        try {
            //创建一个ObjectOutputStream输出流
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            //将对象序列化到文件s
            SerializablePerson bean = new SerializablePerson("whhhh", 18, "programer");
            oos.writeObject(bean);

            //创建一个ObjectInputStream输入流
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            bean = (SerializablePerson) ois.readObject();
            System.out.println("readObject==>" + bean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
             ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"))) {
            SerializablePerson person = new SerializablePerson("whhh", 23, "sjhdb");
            oos.writeObject(person);
            ArrayList list = (ArrayList) ios.readObject();
            System.out.println("list==>" + list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeMap() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
            ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"));
            SerializablePerson person = new SerializablePerson("9龙", 23, "dfkd");
            oos.writeObject(person);
            HashMap map = (HashMap) ios.readObject();
            System.out.println(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SerializablePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
    }

    /**
     * 自定义序列化
     * 与 writeList() 方法同在
     *
     * @return
     * @throws ObjectStreamException
     */
    private Object writeReplace() throws ObjectStreamException {
        ArrayList<Object> list = new ArrayList<>(2);
        list.add(this.name);
        list.add(this.age);
        list.add(this.job);
        return list;
    }

    /**
     * 与 writeMap() 方法同在
     * @return
     * @throws ObjectStreamException
     */
//    private Object readResolve() throws ObjectStreamException {
//        return new SerializablePerson("wjjj", 45, "sfg");
//    }

    public static void main(String[] args) {
//        writeObject();
        writeList();
//        writeMap();//???
    }
}
