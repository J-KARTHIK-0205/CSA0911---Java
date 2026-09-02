import java.util.LinkedHashMap;
import java.util.Map;

public class Q76_LinkedHashMapInsertionOrder {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("Charlie", 3);
        map.put("Alpha", 1);
        map.put("Bravo", 2);

        System.out.println("Keys in insertion order:");
        for (String key : map.keySet()) {
            System.out.println(key + " -> " + map.get(key));
        }
    }
}
