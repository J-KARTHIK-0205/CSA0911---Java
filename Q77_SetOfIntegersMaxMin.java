import java.util.HashSet;
import java.util.Set;

public class Q77_SetOfIntegersMaxMin {
    public static void main(String[] args) {
        Set<Integer> numbers = new HashSet<>();
        numbers.add(23);
        numbers.add(4);
        numbers.add(67);
        numbers.add(12);
        numbers.add(89);

        int max = java.util.Collections.max(numbers);
        int min = java.util.Collections.min(numbers);

        System.out.println("Set: " + numbers);
        System.out.println("Maximum value: " + max);
        System.out.println("Minimum value: " + min);
    }
}
