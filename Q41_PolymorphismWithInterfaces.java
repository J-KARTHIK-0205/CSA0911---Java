interface Drawable {
    void draw();
}

class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle.");
    }
}

class Square implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing a Square.");
    }
}

public class Q41_PolymorphismWithInterfaces {
    public static void main(String[] args) {
        Drawable[] shapes = { new Circle(), new Square() };
        for (Drawable d : shapes) {
            d.draw();
        }
    }
}
