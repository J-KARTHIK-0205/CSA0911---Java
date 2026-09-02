import java.util.Scanner;

public class Q18_ProductOfDigits {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        int num = Math.abs(n);
        long product = 1;
        while (num > 0) {
            product *= (num % 10);
            num /= 10;
        }
        System.out.println("Product of digits: " + product);
        sc.close();
    }
}
