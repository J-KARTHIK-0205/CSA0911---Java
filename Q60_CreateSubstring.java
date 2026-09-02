import java.util.Scanner;

public class Q60_CreateSubstring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.print("Enter start index: ");
        int start = sc.nextInt();
        System.out.print("Enter end index: ");
        int end = sc.nextInt();

        System.out.println("Substring: " + str.substring(start, end));
        sc.close();
    }
}
