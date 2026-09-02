import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Q59_MostFrequentWord {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a sentence: ");
        String str = sc.nextLine();

        String[] words = str.toLowerCase().split("\\s+");
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }

        String mostFrequent = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        System.out.println("Most frequent word: " + mostFrequent + " (" + maxCount + " times)");
        sc.close();
    }
}
