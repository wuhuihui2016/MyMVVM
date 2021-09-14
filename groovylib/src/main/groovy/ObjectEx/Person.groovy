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