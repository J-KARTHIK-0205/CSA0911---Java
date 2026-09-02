import java.util.Arrays;
import java.util.Scanner;

public class Q69_ConvertStringToCharArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        char[] chars = str.toCharArray();
        System.out.println("Char array: " + Arrays.toString(chars));
        sc.close();
    }
}
