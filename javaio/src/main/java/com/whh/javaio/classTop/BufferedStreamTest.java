package com.whh.javaio.classTop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedStreamTest {
    private static final byte[] byteArray = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        bufferedOutPutStream(); //写入文本
        bufferedInputStream(); //读出文本
    }

    private static void bufferedOutPutStream() {
        try {
            File file = new File(PathUtils.path + "BufferedStreamTest.txt");
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bos.write(byteArray[0]);
            bos.write(byteArray, 1, byteArray.length - 1);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferedInputStream() {
        try {
            File file = new File(PathUtils.path + "BufferedStreamTest.txt");
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
            for (int i = 0; i < 10; i++) {
                if (bin.available() >= 0) {
                    System.out.println(byteToString((byte) bin.read()));
                }
            }

            bin.mark(6); //书签位置(有毛用啊)
            bin.skip(1); //在已读的基础上跳过15个

            byte[] b = new byte[1024];
            int n1 = bin.read(b, 0, b.length);
            System.out.println("剩余的有效字节数 ： " + n1);//总共26个，已读10个，跳过1个，剩余15个
            printByteValue(b);

            bin.reset(); //清除skip，从已读的10个后的第11个开始读取
            int n2 = bin.read(b, 0, b.length);
            System.out.println("剩余的有效字节数 ： " + n2);
            printByteValue(b);

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private static String byteToString(byte b) {
        byte[] barray = {b};
        return new String(barray);
    }

    private static void printByteValue(byte[] buf) {
        for (byte b : buf) {
            if (b != 0) {
                System.out.print(byteToString(b) + " ");
            }
        }
    }

}
