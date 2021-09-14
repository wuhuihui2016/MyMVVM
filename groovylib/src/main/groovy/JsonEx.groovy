import groovy.json.JsonOutput
import groovy.json.JsonSlurper

//对象转成json字符串
def list = [new ParserPerson(name: 'whh', age: 18),
            new ParserPerson(name: 'whh1', age: 18)]
println "转json输出"
println JsonOutput.toJson(list)
//格式化
println "格式化json输出"
def json = JsonOutput.toJson(list)
println JsonOutput.prettyPrint(json)

//json字符串转成对象
def jsonSluper = new JsonSlurper()
def object = jsonSluper.parse("[{\"age\":18,\"name\":\"whh\"},{\"age\":18,\"name\":\"whh1\"}]".getBytes())
println "json转Object输出"
println object

def object2 = jsonSluper.parse("[{\"abc\":\"whh\"}]".getBytes())
println "abc???"
println object2.abc

class ParserPerson {
    String name
    Integer age
}