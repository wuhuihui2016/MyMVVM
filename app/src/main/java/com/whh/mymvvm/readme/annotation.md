一、注解(链接：https://www.jianshu.com/p/9471d6bcf4cf)
  元注解理解为注解的注解，它是作用在注解中，方便我们使用注解实现想要的功能。
  元注解分别有@Retention、 @Target、 @Document、 @Inherited、@Repeatable（JDK1.8加入）五种。
  1、Retention表示注解存在阶段是保留在源码（编译期），字节码（类加载）或者运行期（JVM中运行）
     @Retention(RetentionPolicy.SOURCE)，注解仅存在于源码中，在class字节码文件中不包含
     @Retention(RetentionPolicy.CLASS)， 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得
     @Retention(RetentionPolicy.RUNTIME)， 注解会在class字节码文件中存在，在运行时可以通过反射获取到
  2、@Target元注解表示我们的注解作用的范围就比较具体了，可以是类，方法，方法参数变量等，同样也是通过枚举类ElementType表达作用类型
     @Target(ElementType.TYPE) 作用接口、类、枚举、注解，一般使用ElementType.TYPE
     @Target(ElementType.FIELD) 作用属性字段、枚举的常量
     @Target(ElementType.METHOD) 作用方法
     @Target(ElementType.PARAMETER) 作用方法参数
     @Target(ElementType.CONSTRUCTOR) 作用构造函数
     @Target(ElementType.LOCAL_VARIABLE)作用局部变量
     @Target(ElementType.ANNOTATION_TYPE)作用于注解（@Retention注解中就使用该属性）
     @Target(ElementType.PACKAGE) 作用于包
     @Target(ElementType.TYPE_PARAMETER) 作用于类型泛型，即泛型方法、泛型类、泛型接口 （jdk1.8加入）
     @Target(ElementType.TYPE_USE) 类型使用.可以用于标注任意类型除了 class （jdk1.8加入）
  3、@Documented能够将注解中的元素包含到 Javadoc 中去
  4、@Inherited 一个被@Inherited注解了的注解修饰了一个父类，如果他的子类没有被其他注解修饰，则它的子类也继承了父类的注解。
  5、@Repeatable 被这个元注解修饰的注解可以同时作用一个对象多次，但是每次作用注解又可以代表不同的含义
  
  注解的作用
  提供信息给编译器： 编译器可以利用注解来检测出错误或者警告信息，打印出日志。
  编译阶段时的处理： 软件工具可以用来利用注解信息来自动生成代码、文档或者做其它相应的自动处理。
  运行时处理： 某些注解可以在程序运行的时候接受代码的提取，自动做相应的操作。
  注解能够提供元数据，转账例子中处理获取注解值的过程是我们开发者直接写的注解提取逻辑，处理提取和处理 Annotation 的代码统称为 APT（Annotation Processing Tool)。转账例子中的processAnnotationMoney方法就可以理解为APT工具类。

二、APT(Annotation Process Tool)，
    一种在代码编译时处理注解，按照一定的规则，生成相应的java文件，多用于对自定义注解的处理。
    目前比较流行的Dagger2, ButterKnife, EventBus3都是采用APT技术，对运行时的性能影响很小。
    相当于在javac编译源文件时被javac调起的一个小程序
 　　TODO　疑问：为何注解了@AutoService(Processor.class)，也在
 　　src/main/resources/META-INF/services/javax.annotation.processing.Processor　注册了，代码却并没有生成？