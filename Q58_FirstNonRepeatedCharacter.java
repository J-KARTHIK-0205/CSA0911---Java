import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Q58_FirstNonRepeatedCharacter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        Map<Character, Integer> countMap = new LinkedHashMap<>();
        for (char c : str.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        Character result = null;
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                result = entry.getKey();
                break;
            }
        }

        if (result != null) {
            System.out.println("First non-repeated character: " + result);
        } else {
            System.out.println("No non-repeated character found.");
        }
        sc.close();
    }
}
