import java.util.Scanner;

public class Q52_CheckStringContainsOnlyDigits {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        boolean onlyDigits = str.matches("\\d+");
        if (onlyDigits) {
            System.out.println("The string contains only digits.");
        } else {
            System.out.println("The string does not contain only digits.");
        }
        sc.close();
    }
}
