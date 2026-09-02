abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void eat();
    public abstract void sleep();
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println(name + " the dog eats kibble.");
    }

    @Override
    public void sleep() {
        System.out.println(name + " the dog sleeps in its bed.");
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println(name + " the cat eats fish.");
    }

    @Override
    public void sleep() {
        System.out.println(name + " the cat sleeps on the couch.");
    }
}

public class Q42_PolymorphismWithAbstractClasses {
    public static void main(String[] args) {
        Animal[] animals = { new Dog("Rex"), new Cat("Whiskers") };
        for (Animal a : animals) {
            a.eat();
            a.sleep();
        }
    }
}
