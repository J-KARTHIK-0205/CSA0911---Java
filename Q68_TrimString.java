import java.util.Scanner;

public class Q68_TrimString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string (with extra spaces): ");
        String str = sc.nextLine();

        String trimmed = str.trim();
        System.out.println("Trimmed string: \"" + trimmed + "\"");
        sc.close();
    }
}
