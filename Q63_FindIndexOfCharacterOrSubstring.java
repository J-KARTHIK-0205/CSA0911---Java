import java.util.Scanner;

public class Q63_FindIndexOfCharacterOrSubstring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.print("Enter character or substring to find: ");
        String target = sc.nextLine();

        int index = str.indexOf(target);
        if (index != -1) {
            System.out.println("Found at index: " + index);
        } else {
            System.out.println("Not found in the string.");
        }
        sc.close();
    }
}
