import java.util.Scanner;

public class Q15_DecimalToBinary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a decimal number: ");
        int n = sc.nextInt();

        if (n == 0) {
            System.out.println("Binary: 0");
            sc.close();
            return;
        }

        StringBuilder binary = new StringBuilder();
        int num = Math.abs(n);
        while (num > 0) {
            binary.insert(0, num % 2);
            num /= 2;
        }
        System.out.println("Binary: " + (n < 0 ? "-" : "") + binary.toString());
        sc.close();
    }
}
