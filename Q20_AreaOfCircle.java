import java.util.Scanner;

public class Q20_AreaOfCircle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the radius: ");
        double radius = sc.nextDouble();

        double area = Math.PI * radius * radius;
        System.out.printf("Area of the circle: %.2f%n", area);
        sc.close();
    }
}
