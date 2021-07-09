package com.whh.others.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Externalizable （extends java.io.Serializable）序列化
 * 实现 Externalizable 接口需要注意：
 *   1、bean类必须有无参构造函数(默认调用无参构造函数)，否则报错：no valid constructor
 *   2、writeExternal、readExternal 中的字段使用必须成对出现，否则报错：EOFException
 *   3、writeExternal、readExternal 中的字段使用必须顺序出现，否则报错：OptionalDataException
 *
 * author:wuhuihui 2021.07.09
 */
public class ExternalizablePerson implements Externalizable {

    String name;
    int age;

    public ExternalizablePerson() {
    }

    public ExternalizablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
        name = (String) in.readObject();
        age = in.readInt();
    }

    @Override
    public String toString() {
        return "ExternalizablePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        ExternalizablePerson person = new ExternalizablePerson("whh", 18);
        System.out.println("person==>" + person.toString());

        byte[] data;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(person);

            data = out.toByteArray();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            person = (ExternalizablePerson) ois.readObject();

            System.out.println("ExternalizablePerson==>" + person.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
