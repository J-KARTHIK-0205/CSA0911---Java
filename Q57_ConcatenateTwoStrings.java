import java.util.Scanner;

public class Q57_ConcatenateTwoStrings {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first string: ");
        String s1 = sc.nextLine();
        System.out.print("Enter second string: ");
        String s2 = sc.nextLine();

        System.out.println("Concatenated string: " + s1.concat(s2));
        sc.close();
    }
}
