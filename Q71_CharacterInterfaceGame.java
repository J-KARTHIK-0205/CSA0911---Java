interface Character {
    void move();
    void attack();
    void takeDamage(int amount);
}

class Hero implements Character {
    private int health = 100;

    @Override
    public void move() {
        System.out.println("Hero moves swiftly across the battlefield.");
    }

    @Override
    public void attack() {
        System.out.println("Hero attacks with a sword!");
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println("Hero takes " + amount + " damage. Health: " + health);
    }
}

class Villain implements Character {
    private int health = 120;

    @Override
    public void move() {
        System.out.println("Villain stalks in the shadows.");
    }

    @Override
    public void attack() {
        System.out.println("Villain casts a dark spell!");
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println("Villain takes " + amount + " damage. Health: " + health);
    }
}

class Monster implements Character {
    private int health = 200;

    @Override
    public void move() {
        System.out.println("Monster lumbers forward.");
    }

    @Override
    public void attack() {
        System.out.println("Monster smashes with its claws!");
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println("Monster takes " + amount + " damage. Health: " + health);
    }
}

public class Q71_CharacterInterfaceGame {
    public static void main(String[] args) {
        Character[] characters = { new Hero(), new Villain(), new Monster() };
        for (Character c : characters) {
            c.move();
            c.attack();
            c.takeDamage(20);
        }
    }
}
