import java.util.Scanner;

public class Q21_VolumeOfSphere {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the radius: ");
        double radius = sc.nextDouble();

        double volume = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
        System.out.printf("Volume of the sphere: %.2f%n", volume);
        sc.close();
    }
}
