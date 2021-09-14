println("1.闭包定义与使用")
//无参数的
def closure = {
    println "closure：hello groovy!" //closure：hello groovy!
}
closure()
//closure.call() //等效于closure()

//带参数的闭包
def closure1 = { String name, int age ->
    println "closure1：hello ${name},age ${age}" //closure1：hello whh:age 18
}
closure1.call("whh", 18)

//带默认参数
def closure2 = {
    println "closure2：hello ${it}" //closure2：hello whh
}
closure2.call("whh")

//闭包的返回值
def closure3 = {
    println "closure3：hello ${it}" //closure3：hello whh
    return "123"
}
def result = closure3.call("whh")
println "closure3：result=" + result //closure3：result=123


/**
 * 匿名内联函数，也称为一个闭包。
 * 基本类型相关的API
 */
println("2.匿名内联函数，基本类型相关的API")
int x = fab(4)

/**
 * 从 1 - number 依次相乘
 * @param number
 * @return
 */
int fab(int number) {
    int result = 1
    1.upto(number, { num -> result *= num })
    return result
}

println "fab：$x"

/**
 * 从 number - 1 依次相乘
 * @param number
 * @return
 */
int fab2(int number) {
    int result = 1
    number.downto(1) {
        num -> result *= num
    }
    return result
}

println "fab2：${fab2(4)}"

/**
 * 0 + number
 * @param number
 * @return
 */
int sum(int number) {
    int result = 0
    number.times {
        num -> result += num
    }
    return result
}

println "sum：${sum(3)}"


/**
 * 和String相关的API
 */
println("3.和String相关的API")
String str = "3 and 4 is 7，我今年18岁"
//each遍历(multiply重复输出)
str.each {
    String s -> print s.multiply(1)
}

println("") //特殊处理，由于上面是print，下面是println,
//find查找符合条件的第一个字符
println str.find {
    String s -> s.isNumber() //3
}

//findAll 查找符合条件的所有字符
def list = str.findAll {
    String s -> s.isNumber()
}
println list.toListString() //[3, 4, 7, 1, 8]

//any 查找是否存在符合条件的字符
def result1 = str.any {
    String s -> s.isNumber()
}
println result1 //true

//every查找是否所有字符都符合条件
def result2 = str.every {
    String s -> s.isNumber()
}
println result2 //false

//对str的每一位单独操作后的结果保存到一个集合中
def list1 = str.collect {
    it.toUpperCase()
}
println list1.toListString() //[3,  , A, N, D,  , 4,  , I, S,  , 7, ，, 我, 今, 年, 1, 8, 岁]


println("4.闭包的三个重要变量：this,owner,deleate")
/**
 * 闭包的三个重要变量：this,owner,deleate
 */
//在同一个闭包中，都是相同的对象
def scriptClouser = {
    println this//代表闭包定义处的类
    println owner//代表闭包定义处的类或者对象
    println delegate//代表任意对象，delegate默认为owner指向的对象
}
scriptClouser.call()

//在普通类或方法中定义闭包，三者是相同的
class ClouserPerson {
    def static classClouser = {
        println "classClouser:" + this
        println "classClouser:" + owner
        println "classClouser:" + delegate
    }

    def static method() {
        def classClouser = {
            println "methodclassClouser:" + this
            println "methodclassClouser:" + owner
            println "methodclassClouser:" + delegate
        }
        classClouser.call()
    }
}

ClouserPerson.classClouser.call()
ClouserPerson.method()

//闭包内定义闭包    this内部对象    owner和delegate是外部对象
def nestClouser = {
    def innerClouser = {
        println "innerClouser:" + this
        println "innerClouser:" + owner
        println "innerClouser:" + delegate
    }
    innerClouser.call()
}
nestClouser.call()

//修改默认的delegate对象
ClouserPerson p = new ClouserPerson()
def nestClouser1 = {
    def innerClouser = {
        println "innerClouser:" + this
        println "innerClouser:" + owner
        println "innerClouser:" + delegate
    }
    innerClouser.delegate = p
    innerClouser.call()
}
nestClouser1.call()

/**
 * 闭包的委托策略
 */
class Student {
    String name
    def pretty = { "My name is ${name}" }

    def call() {
        pretty.call()
    }
}

def student = new Student(name: 'jett')

class Teacher {
    String name
}

def teacher = new Teacher(name: 'andy')
student.pretty.delegate = teacher
//闭包的委托策略
student.pretty.resolveStrategy = Closure.DELEGATE_FIRST

println student.toString()














