import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class JsonEx {
    static void main(String[] args) {
        //对象转成json字符串
        def list = [new Person(name: 'jett', age: 18),
                    new Person(name: 'lance', age: 18)]
        println JsonOutput.toJson(list)
        //格式化
        def json = JsonOutput.toJson(list)
        println JsonOutput.prettyPrint(json)

        //json字符串转成对象
        def jsonSluper = new JsonSlurper()
        def object = jsonSluper.parse("[{\"age\":18,\"name\":\"jett\"},{\"age\":18,\"name\":\"lance\"}]".getBytes())
        println object

        def object2 = jsonSluper.parse("[{\"abc\":\"jett\"}]".getBytes())
        println object2.abc
    }


    class Person {
        String name
        Integer age
    }
}









