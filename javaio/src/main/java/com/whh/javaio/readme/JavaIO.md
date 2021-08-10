DataOutputStream out = new DataOutputStream(
					new	BufferedOutputStream(
							new FileOutputStream(
									new File(PathUtils.path + "tataStreamTest.txt"))));
读入，写出的设定，是相对于内存而言的，与程序员思想相反；
