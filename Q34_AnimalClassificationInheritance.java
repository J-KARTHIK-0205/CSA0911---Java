class Animal {
    protected String name;
    protected String habitat;

    public Animal(String name, String habitat) {
        this.name = name;
        this.habitat = habitat;
    }

    public void info() {
        System.out.println(name + " lives in " + habitat + ".");
    }
}

class Mammal extends Animal {
    public Mammal(String name, String habitat) {
        super(name, habitat);
    }

    public void eat() {
        System.out.println(name + " eats and nurses its young with milk.");
    }

    public void reproduce() {
        System.out.println(name + " gives birth to live young.");
    }
}

class Reptile extends Animal {
    public Reptile(String name, String habitat) {
        super(name, habitat);
    }

    public void eat() {
        System.out.println(name + " eats and is cold-blooded.");
    }

    public void reproduce() {
        System.out.println(name + " lays eggs on land.");
    }
}

class Bird extends Animal {
    public Bird(String name, String habitat) {
        super(name, habitat);
    }

    public void eat() {
        System.out.println(name + " eats seeds or insects.");
    }

    public void reproduce() {
        System.out.println(name + " lays eggs in a nest.");
    }
}

public class Q34_AnimalClassificationInheritance {
    public static void main(String[] args) {
        Mammal lion = new Mammal("Lion", "Savannah");
        Reptile snake = new Reptile("Snake", "Desert");
        Bird eagle = new Bird("Eagle", "Mountains");

        lion.info();
        lion.eat();
        lion.reproduce();

        snake.info();
        snake.eat();
        snake.reproduce();

        eagle.info();
        eagle.eat();
        eagle.reproduce();
    }
}
