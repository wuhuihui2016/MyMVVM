//1.在groovy中所有的类型   默认都是public
//2.所有的类都是继承自GroovyObject
class Person implements DefaultAction {
    java.lang.String name
    Integer age

    def increateseAge(Integer year) {
        this.age += year
    }

    @Override
    void eat() {
        println 'I can eat!'
    }
}

trait DefaultAction {
    abstract void eat()

    void play() {
        println 'I can play!'
    }
}

/**
 * 接口中不能定义非public方法的
 */
interface Action {
    void eat()

    void drink()

    void play()
}


def person = new Person(name: 'whh', age: 18)
println "name=" + person.name + "  age=" + person.age
println "name=" + person.getName() + "  age=" + person.getAge()

person.increateseAge(10)
println "name=" + person.name + "  age=" + person.age

person.play()
person.eat()
