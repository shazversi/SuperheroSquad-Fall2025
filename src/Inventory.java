import java.util.ArrayList;
import java.util.List;

/**
 * Inventory.java
 * Manages the Player's collection of items, with a fixed capacity of 13 (Q27).
 * Note: Requires the Item class to be available.
 */
public class Inventory {

    private static final int MAX_CAPACITY = 13;
    private final List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    // FR5: Pick up an item
    public boolean addItem(Item item) {
        if (items.size() >= MAX_CAPACITY) {
            System.out.println("There is no more available space in inventory.");
            return false;
        }
        items.add(item);
        System.out.println(item.getName() + " added to inventory.");
        return true;
    }

    // FR6 (Drop) & FR8 (Consume)
    public boolean removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            return true;
        }
        return false;
    }

    // Helper for command parsing (e.g., for 'use Trident Key')
    public boolean containsItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    // FR23: Check inventory
    public void displayInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("--- Inventory (Capacity: " + items.size() + "/" + MAX_CAPACITY + ") ---");
        for (Item item : items) {
            System.out.println("* " + item.toString());
        }
        System.out.println("---------------------------------");
    }

    public List<Item> getItems() {
        return items;
    }
}