import java.util.Scanner;

public class Q51_CountOccurrencesOfCharacter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.print("Enter a character to count: ");
        char ch = sc.next().charAt(0);

        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        System.out.println("'" + ch + "' occurs " + count + " time(s) in the string.");
        sc.close();
    }
}
