import java.util.Map;
import java.util.TreeMap;

public class Q74_TreeMapIterateEntries {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("Banana", 3);
        map.put("Apple", 5);
        map.put("Cherry", 8);

        System.out.println("TreeMap entries (sorted by key):");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
