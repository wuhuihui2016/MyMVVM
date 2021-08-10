package com.whh.javaio.classTop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Duration;
import java.time.Instant;

/**
 * 1、RandomAccessFile 指哪打哪，支持其随机访问的特性
 *    使用场景就是网络请求中的多线程下载及断点续传
 *  2、FileChannel 的使用见 AndroidBase peoject 中 #com.whh.androidbase.utils.FileUtils
 *    用于大文件的传输，效率很高，且使用简单
 */
public class FileChannelTest {
	
	public static void main(String[] args) {
		File sourceFile = new File("D://alvin//IOtest//file1.mp4");
		File targetFile = new File("D://file1-1.mp4");
		targetFile.deleteOnExit();
		try {
			targetFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		copyFileByStream(sourceFile, targetFile);
		copyFileByFileChannel(sourceFile, targetFile);
	}
	
	private static void copyFileByFileChannel(File sourceFile,File targetFile){
		Instant begin = Instant.now();
		
		RandomAccessFile randomAccessSourceFile;
		RandomAccessFile randomAccessTargetFile;
		
		try {
			/*"r"：以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
			"rw": 打开以便读取和写入。
			"rws": 打开以便读取和写入。相对于 "rw"，"rws" 还要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备。
			"rwd" : 打开以便读取和写入，相对于 "rw"，"rwd" 还要求对“文件的内容”的每个更新都同步写入到基础存储设备。*/
			randomAccessSourceFile = new RandomAccessFile(sourceFile, "r");
			randomAccessTargetFile = new RandomAccessFile(targetFile, "rw");
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		FileChannel sourceFileChannel = randomAccessSourceFile.getChannel();
		FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
		try {
			while(sourceFileChannel.read(byteBuffer) != -1) {
				byteBuffer.flip();
				targetFileChannel.write(byteBuffer);
				byteBuffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sourceFileChannel.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			try {
				targetFileChannel.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("total spent: " + Duration.between(begin, Instant.now()).toMillis());
	}
	
	private static void copyFileByStream(File sourceFile,File targetFile) {
		Instant begin = Instant.now();
		
		FileInputStream fis;
		FileOutputStream fos;
		
		try {
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(targetFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		byte[] readed = new byte[1024*1024];
		try {
			while (fis.read(readed) != -1) {
				fos.write(readed);
			}
		} catch( IOException e){
			e.printStackTrace();
		} finally {
			try{
				fos.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		System.out.println("total spent: " + Duration.between(begin, Instant.now()).toMillis());
		
	}

}
