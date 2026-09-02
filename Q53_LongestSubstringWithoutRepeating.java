import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Q53_LongestSubstringWithoutRepeating {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLength = 0, start = 0;
        for (int end = 0; end < str.length(); end++) {
            char c = str.charAt(end);
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= start) {
                start = lastSeen.get(c) + 1;
            }
            lastSeen.put(c, end);
            maxLength = Math.max(maxLength, end - start + 1);
        }

        System.out.println("Length of the longest substring without repeating characters: " + maxLength);
        sc.close();
    }
}
