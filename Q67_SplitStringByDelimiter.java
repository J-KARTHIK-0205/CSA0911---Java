import java.util.Arrays;
import java.util.Scanner;

public class Q67_SplitStringByDelimiter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.print("Enter delimiter: ");
        String delimiter = sc.nextLine();

        String[] parts = str.split(java.util.regex.Pattern.quote(delimiter));
        System.out.println("Substrings: " + Arrays.toString(parts));
        sc.close();
    }
}
