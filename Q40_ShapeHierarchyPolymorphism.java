abstract class Shape {
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    private double length, width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}

public class Q40_ShapeHierarchyPolymorphism {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(4), new Rectangle(5, 3) };
        for (Shape s : shapes) {
            System.out.printf("%s -> Area: %.2f, Perimeter: %.2f%n",
                    s.getClass().getSimpleName(), s.calculateArea(), s.calculatePerimeter());
        }
    }
}
