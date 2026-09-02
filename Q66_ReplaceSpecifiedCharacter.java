import java.util.Scanner;

public class Q66_ReplaceSpecifiedCharacter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();
        System.out.print("Enter character to replace: ");
        char oldCh = sc.next().charAt(0);
        System.out.print("Enter replacement character: ");
        char newCh = sc.next().charAt(0);

        String result = str.replace(oldCh, newCh);
        System.out.println("Result: " + result);
        sc.close();
    }
}
