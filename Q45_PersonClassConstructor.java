public class Q45_PersonClassConstructor {
    public static void main(String[] args) {
        Person p = new Person("Alice", 30);
        p.printDetails();
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printDetails() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
