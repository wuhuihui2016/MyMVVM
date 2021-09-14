package com.whh.javaio.classTop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 编码获取：getEncoding
 */
public class InputStreamReaderTest {

	public static void testISRDefaultEncoder(InputStream is){
		try{


//			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(is));

			String string;
			while ((string = br.readLine()) != null) {
//				System.out.println("code: " + isr.getEncoding());
				System.out.println(string);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void testISRGBK(InputStream is){
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(is,"GBK");
			BufferedReader gbkBr = new BufferedReader(inputStreamReader);
			String string;
			while ((string = gbkBr.readLine()) != null) {
				System.out.println("code: " + inputStreamReader.getEncoding());
				System.out.println(string);
			}
			gbkBr.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public static void testISRUTF8(InputStream is){
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
			BufferedReader gbkBr = new BufferedReader(inputStreamReader);
			String string;
			while ((string = gbkBr.readLine()) != null) {
				System.out.println("code: " + inputStreamReader.getEncoding());
				System.out.println(string);
			}
			gbkBr.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) throws IOException {
		//？？？？？？？？？？？？？？
		//创建过程是否过于麻烦？？？？？
		testISRDefaultEncoder(
				new FileInputStream(
						new File(PathUtils.path + "OutputStreamWriter.txt")));
		testISRGBK(
				new FileInputStream(
						new File(PathUtils.path + "OutputStreamWriter.txt")));
		testISRUTF8(
				new FileInputStream(
						new File(PathUtils.path + "OutputStreamWriter.txt")));
	}

}
