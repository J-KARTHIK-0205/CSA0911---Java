import java.util.HashSet;
import java.util.Set;

public class Q73_HashSetRemoveElement {
    public static void main(String[] args) {
        Set<String> fruits = new HashSet<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Mango");

        System.out.println("Before removal: " + fruits);
        fruits.remove("Banana");
        System.out.println("After removing 'Banana': " + fruits);
    }
}
