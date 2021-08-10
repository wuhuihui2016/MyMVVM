package com.whh.javaio.classTop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.processing.Filer;

public class BufferedWriteReaderTest {

    /**
     * 将 srcfile 文件内容写入 dstFile
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        File srcfile = new File(PathUtils.path + "BufferedReader.txt");
        File dstFile = new File(PathUtils.path + "BufferedWrite.txt");
        System.out.println("srcfile path is " + srcfile.getPath());
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(dstFile));
        BufferedReader br = new BufferedReader(new FileReader(srcfile));

        char[] string = new char[1024]; // 请注意此处不是byte，而是char
      
        while ((br.read(string))!= -1) {
			bw.write(string);
		}
        br.close();
        bw.flush();
        bw.close();
	}

}
