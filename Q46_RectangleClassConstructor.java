public class Q46_RectangleClassConstructor {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(10, 5);
        System.out.println("Area of rectangle: " + rect.calculateArea());
    }
}

class Rectangle {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double calculateArea() {
        return length * width;
    }
}
