import java.util.Scanner;

public class Q62_CheckStringEndsWithSuffix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.print("Enter the suffix to check: ");
        String suffix = sc.nextLine();

        if (str.endsWith(suffix)) {
            System.out.println("The string ends with \"" + suffix + "\".");
        } else {
            System.out.println("The string does not end with \"" + suffix + "\".");
        }
        sc.close();
    }
}
