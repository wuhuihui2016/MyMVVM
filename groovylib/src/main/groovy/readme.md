Android studio 运行 grovvy [https://blog.csdn.net/aha_jasper/article/details/104810684]

1、new module -> java library -> gradle 文件
  plugins {
    id 'java-library'
    id 'groovy'
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  dependencies {
    implementation localGroovy()
  }

  task renameGroovyToJava {
    doLast{
      delete "$buildDir/classes/java"
      File file = new File("$buildDir/classes/groovy")
      println file.renameTo("$buildDir/classes/java")
    }
  }

  compileJava.finalizedBy compileGroovy
  compileGroovy.finalizedBy renameGroovyToJava

2、使用 grovvy 脚本运行
   android studio 的标题栏进入 Tools/Groovy Console，
   在打开的窗口中编辑 grovvy 代码或 java 式的 grovvy 代码均可直接运行

3、运行 Java 式的 grovvy 代码 (解决问题：直接运行 Test.grovvy 报错：错误: 找不到或无法加载主类 Test)
   a.选择工具栏：Edit Configurations...；
   b.在弹出的页面点击左上角的加号 Add New Configuration，选择groovy；
   c.填写 Name、Script path、module，Name 需与 groovy 文件相名，Script path为 groovy 文件路径，module 选择当前 module；
   d.点击工具栏的运行按钮即可运行该文件

4、注意事项
   a.Edit Configurations 必须选定 module！


语言基础学习文件说明：
1、<String.groovy> 字符串及变量定义使用
2、<ObjectEx.groovy> 对象创建及方法的调用
3、<JsonEx.groovy>、<XmlEx.groovy> json、xml解析
4、<FileOperator.groovy> 文件操作
5、<ClosureTest.groovy> 闭包的使用
6、<DataStructureDemo.groovy> 数据集合的使用