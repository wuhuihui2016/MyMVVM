package Object

def person = new Person(name: 'whh', age: 18)
println "name=" + person.name + "  age=" + person.age
println "name=" + person.getName() + "  age=" + person.getAge()

person.increateseAge(10)
println "name=" + person.name + "  age=" + person.age

person.play()
person.eat()
