package com.whh.javaio.classTop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用 DataOutputStream、DataInputStream 读写
 * 读写顺序必须一一对应
 */
public class DataStreamTest {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        testDataOutPutStream();
        testDataInputStreamI();
    }

    private static void testDataOutPutStream() {

        try {
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(PathUtils.path + "tataStreamTest.txt"))));

            out.writeBoolean(true);
            out.writeByte((byte) 0x41); //78
            out.writeChar((char) 0x4243); //0
            out.writeShort((short) 0x4445); //9
            out.writeInt(0x12345678); //87654321
            out.writeLong(0x987654321L); //1f616263646566

            out.writeUTF("abcdefghijklmnopqrstuvwxyz严12");
            out.writeLong(0x023433L);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 读取内容，需与写入顺序一致
     */
    private static void testDataInputStreamI() {
        try {
            File file = new File(PathUtils.path + "tataStreamTest.txt");
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            System.out.println("long:" + Long.toHexString(in.readLong()));
            System.out.println("bool:" + in.readBoolean());
            System.out.println("byte" + byteToHexString(in.readByte()));
            System.out.println("char:" + charToHexString(in.readChar()));
            System.out.println("short:" + shortToHexString(in.readShort()));
            System.out.println("int:" + Integer.toHexString(in.readInt()));
            System.out.println("long:" + Long.toHexString(in.readLong()));
            System.out.println("UTF:" + in.readUTF()); //没有输出？？
            System.out.println("long:" + Long.toHexString(in.readLong())); //没有输出？？
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // 打印byte对应的16进制的字符串
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

    // 打印char对应的16进制的字符串
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

    // 打印short对应的16进制的字符串
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }

}
