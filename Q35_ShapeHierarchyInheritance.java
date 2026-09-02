abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    public abstract double calculateArea();
    public abstract double calculatePerimeter();
}

class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
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

class Square extends Shape {
    private double side;

    public Square(String color, double side) {
        super(color);
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * side;
    }
}

class Triangle extends Shape {
    private double base, height, a, b, c;

    public Triangle(String color, double base, double height, double a, double b, double c) {
        super(color);
        this.base = base;
        this.height = height;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public double calculatePerimeter() {
        return a + b + c;
    }
}

public class Q35_ShapeHierarchyInheritance {
    public static void main(String[] args) {
        Shape circle = new Circle("Red", 5);
        Shape square = new Square("Blue", 4);
        Shape triangle = new Triangle("Green", 6, 4, 5, 5, 6);

        Shape[] shapes = {circle, square, triangle};
        for (Shape s : shapes) {
            System.out.printf("%s shape -> Area: %.2f, Perimeter: %.2f%n",
                    s.color, s.calculateArea(), s.calculatePerimeter());
        }
    }
}
