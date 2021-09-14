def word = 'whh'
println(word)
def word2 = "hello,$word"
println(word2)
def word3 = '''hello word'''
println(word3)

//单引号、双引号、三引号没差别啊。。

def sum="${3+2},${word}"
println sum

def string='hello'
def string2='el'
//groovy中常用的string相关的API
println string>string2 //比较字符串大小 true
//字符串取下标字符
println string[1..2] //el
//字符串减法
println string.minus(string2) //hlo
//逆序
println string.reverse() //olleh
//首字母大写
println string.capitalize() //Hello
//字符串中是否有数字字符
println string.isEmpty() //false

