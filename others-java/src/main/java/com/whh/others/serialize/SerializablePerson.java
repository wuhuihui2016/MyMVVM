package com.whh.others.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 序列化：将数据结构或对象转换为二进制的过程，用于保存，或者网络传输。
 * 反序列化：将在序列化过程中所生成的二进制串转换成数据结构或对象的过程。
 * 持久化：把数据结构或对象存储起来(硬盘)

 * 使用transient修饰，让某个变量不被序列化。
 * 序列化对象的引用类型成员变量，也必须是可序列化的，否则，会报错。
 * 反序列化时必须有序列化对象的class文件。
 * 当通过文件、网络来读取序列化后的对象时，必须按照实际写入的顺序读取。
 * 单例类序列化，需要重写readResolve()方法；否则会破坏单例原则。
 * 同一对象序列化多次，只有第一次序列化为二进制流，以后都只是保存序列化编号，不会重复序列化。
 * <p>
 * author:wuhuihui 2021.07.07
 */
public class SerializablePerson implements Serializable {

    //不需要序列化姓名和年龄，在序列化写入后，读取到的字段为空、或0、或false
    //SerializablePerson{name='null', age=0, job='programer'}
    transient String name;
    transient int age;
    String job;
    Student student;

    public SerializablePerson(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public SerializablePerson(String name, Student student) {
        this.name = name;
        this.student = student;
    }

    @Override
    public String toString() {
        return "SerializablePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                ", student=" + student +
                '}';
    }

    static class Student {
        String ID;

        public Student(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "ID='" + ID + '\'' +
                    '}';
        }
    }

    /**
     * 序列化写入文本
     */
    //C 盘拒绝访问！(java.io.FileNotFoundException: C:\whhh0707.txt (拒绝访问。))
    static String fileName = "D:\\whhh0707.txt";

    /**
     * 111原生序列化方法
     */
    public static void write2ReadObject() {
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


    /**
     * 2222自定义序列化方法
     * @return
     */
//    public static void writeList() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
//             ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"))) {
//            SerializablePerson person = new SerializablePerson("whhh", 23, "sjhdb");
//            oos.writeObject(person);
//            ArrayList list = (ArrayList) ios.readObject();
//            System.out.println("list==>" + list);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 2222自定义序列化
     * 与 writeList() 方法同在
     *
     * @return
     * @throws ObjectStreamException
     */
//    private Object writeReplace() throws ObjectStreamException {
//        ArrayList<Object> list = new ArrayList<>(2);
//        list.add(this.name);
//        list.add(this.age);
//        list.add(this.job);
//        return list;
//    }


    //333自定义序列化，写 JDK 能够识别的方法writeObject、readObject
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
    }

    //333自定义序列化，写 JDK 能够识别的方法writeObject、readObject
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        write2ReadObject(); //原生序列化方法
//        writeList(); //222

        //333调用自定义的 writeObject、readObject
        String customFile = "D:\\custom-whhh0707.txt";
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(customFile));
        //调用我们自定义的writeObject()方法
        out.writeObject("whh-custom");
        out.close();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(customFile));
        System.out.println("custom..." + in.readObject());

        //444 SerializablePerson 引用非序列化的 student ，可行！
        Student student = new Student("23015");
        SerializablePerson person = new SerializablePerson("whh", student);
        System.out.println("person-student==>" + person);
    }

}
