import java.util.HashMap;
import java.util.Map;

public class hashMapExample {
    public static void main(String[] args) {
        // Creating a HashMap
        Map<String, Integer> hashMap = new HashMap<>();

        // Inserting key-value pairs
        hashMap.put("one", 1);
        hashMap.put("two", 2);
        hashMap.put("three", 3);

        // Retrieving values
        System.out.println("Value for key 'two': " + hashMap.get("two")); // Output: 2

        // Updating value for an existing key
        hashMap.put("two", 20);
        System.out.println("Updated value for key 'two': " + hashMap.get("two")); // Output: 20

        // Removing a key-value pair
        hashMap.remove("three");

        // Checking if a key exists
        System.out.println("Contains key 'three': " + hashMap.containsKey("three")); // Output: false
    }
}
