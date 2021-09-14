def file = new File("E:\\private\\whh_git\\MyMVVM\\groovylib\\src\\main\\Groovy\\testfile.txt")
/**
 * 文件初始内容
 123456789
 abcdefghi
 */

//读取文件
file.eachLine { line ->
    println "1.遍历文件行读取：\n$line"
}

//返回所有文本
def text = file.getText()
println "2.获取文本内容：\n$text"

//以List<Stirng>返回文件的每一行
def text2 = file.readLines()
println "3.以List<Stirng>返回文件的每一行：\n${text2.toListString()}"

//以java中的流的方式读取文件内容
def reader = file.withReader { reader ->
    char[] buffer = new char[100]
    reader.read(buffer)
    return buffer
}
println "4.java中的流的方式读取文件：\n$reader"


//写入数据(append() 不是追加内容，而是覆盖原有内容)
file.withWriter { writer ->
    writer.append("123456789\n")
    writer.append("abcdefghi\n")
    writer.append("whhhhhhh")
}
text = file.getText()
println "5.写入数据后，获取文本内容：\n$text"