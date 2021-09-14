//定义list
//def list=new ArrayList()
println("定义list")
def list = [1, 2, 3, 4, 5]
println list.class //class java.util.ArrayList
println list.size() //5
//定义数组
def array = [1, 2, 3, 4, 5] as int[]

//1.添加
list.add(6)
list << 2
println "list:$list" //[1, 2, 3, 4, 5, 6, 2]
def plusList = list + 5
println "plusList:$plusList"  //[1, 2, 3, 4, 5, 6, 2, 5]
plusList.add(3, 9)
println "plusList2:$plusList" //[1, 2, 3, 9, 4, 5, 6, 2, 5]

//2.删除 [1, 2, 3, 4, 5, 6, 2]
//[1, 4, 5, 6]
list.remove(2)  //删除下标位置的对象
list.remove((Object) 2)
list.removeElement(2)
list.removeAll {
    return it % 2 != 0
}
println list - [2, 4] //[6]


//3.查找
def findList = [5, -2, 1, 4, 3]
println "findList:$findList"
//查找满足条件的第一个数据
int result = findList.find {
    return it % 2 == 0
}
println result //-2
//查找所有满足条件的数据
def result2 = findList.findAll({
    return it % 2 != 0
})
println result2 //[5, 1, 3]

//查找是否有满足条件的数据
def result3 = findList.any {
    return it % 2 == 0
}
println result3 //true

//查找是否全部满足条件
def result4 = findList.every {
    return it % 2 == 0
}
println result4 //false

//查找最大值与最小值
def result5 = findList.min {
    return it
}
println "min:$result5" //-2
def result6 = findList.max {
    return Math.abs(it) //绝对值
}
println "max:$result6" //5
//统计
int result7 = findList.count {
    return it > 0
}
println "> 0 count:$result7" //4


//4.排序(绝对值排序)
def sortList = [5, -2, 1, 4, 3]
sortList.sort({ a, b ->
    a == b ? 0 : Math.abs(a) > Math.abs(b) ? 1 : -1
})
println "sortList:$sortList"

//对象排序(长度优先，再字母排序)
def sortStringList = ['aaaaa', 'bbbb', 'cc', 'ddd', 'ee']
sortStringList.sort({ it ->
    return it.size()
})
println "sortStringList$sortStringList" //[cc, ee, ddd, bbbb, aaaaa]


//Range相当于一个轻量级的List
println("")
println("2.Range相当于一个轻量级的List")
def range = 1..5
println range[0] //1
println range.contains(5) //true
println range.from //起点 //1
println range.to   //终点 //5

//遍历
range.each {
    print it
}
for (i in range) {
    print i
}

def getGrade(Number score) {
    def result
    switch (score) {
        case 0..<60:
            result = '不及格'
            break
        case 60..100:
            result = '及格'
            break
        default:
            result = '输入异常'
    }
    return result
}

println getGrade(50) //不及格
println getGrade(80) //及格
println getGrade(120) //输入异常


println("")
println("3.map遍历")
////定义与读取
def colors = [red: 'ff0000', green: '00ff00', blue: '0000ff']
println colors['red'] //ff0000
println colors.red //ff0000
//如果使用colors.class  会把class当成一个键
println colors.getClass() //class java.util.LinkedHashMap

//添加普通对象
colors.yellow = 'ffff00'
println colors //[red:ff0000, green:00ff00, blue:0000ff, yellow:ffff00]
//添加集合对象
colors.map = [key1: 1, key2: 2]
println colors.toMapString() //[red:ff0000, green:00ff00, blue:0000ff, yellow:ffff00, map:[key1:1, key2:2]]

//遍历map
def teachers = [
        1: [number: '001', name: 'whh'],
        4: [number: '004', name: 'alven'],
        3: [number: '003', name: 'lance'],
        2: [number: '002', name: 'leo'],
        6: [number: '006', name: 'allen'],
        5: [number: '005', name: 'whh'],
        7: [number: '007', name: 'derry'],
        8: [number: '008', name: 'whh']
]

//用键值对的方式
teachers.each { def key, def value ->
    println "key=${key}---value=${value}"
}
//用entry对象的方式
teachers.each { def teacher ->
    println "key=${teacher.key}---value=${teacher.value}"
}
//带索引的方式
teachers.eachWithIndex { def teacher, int index ->
    println "index=${index}---key=${teacher.key}---value=${teacher.value}"
}
teachers.eachWithIndex { def key, def value, int index ->
    println "index=${index}---key=${key}---value=${value}"
}

//map的查找
def entry = teachers.find { def teacher ->
    return teacher.value.name == 'whh'
}
println "find$entry" //1={number=001, name=whh}

def entry1 = teachers.findAll { def teacher ->
    return teacher.value.name == 'whh'
}
println "findAll$entry1" //[1:[number:001, name:whh], 8:[number:008, name:whh]]

def count = teachers.count { def teacher ->
    return teacher.value.name == 'whh'
}
println "teachers.count$count" //2

//实现嵌套查询
def number = teachers.findAll { def teacher ->
    return teacher.value.name == 'whh'
}.collect() {
    return it.value.number
}
println "number${number.toListString()}" //[001, 008]

//实现分组查询
def group = teachers.groupBy { def teacher ->
    return teacher.value.name == 'whh' ? 'group1' : 'group2'
}
println "number${group.toMapString()}"

//排序  注意：map会返回一个新的map   list是在原来的list中进行排序
def sort = teachers.sort { def t1, def t2 ->
    return t1.key > t2.key ? 1 : -1
}
println "sort${sort.toMapString()}"