import java.util.Scanner;

public class Q19_PowerOfNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the base: ");
        int base = sc.nextInt();
        System.out.print("Enter the exponent: ");
        int exp = sc.nextInt();

        long result = 1;
        for (int i = 0; i < exp; i++) {
            result *= base;
        }
        System.out.println(base + " ^ " + exp + " = " + result);
        sc.close();
    }
}
