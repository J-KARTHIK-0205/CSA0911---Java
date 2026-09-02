import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q72_ListSortAscendingCollections {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(45, 12, 78, 3, 29, 90, 1));
        System.out.println("Before sorting: " + numbers);

        Collections.sort(numbers);
        System.out.println("After sorting: " + numbers);
    }
}
