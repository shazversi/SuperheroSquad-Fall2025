/**
 * Item.java
 * The base abstract class for all items in the Lost in Atlantis game.
 * Note: Requires the Player class to be available.
 */
public abstract class Item {

    private final String name;
    private final String description;
    private final String itemType;

    public Item(String name, String description, String itemType) {
        this.name = name;
        this.description = description;
        this.itemType = itemType;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getItemType() {
        return itemType;
    }

    // --- Core Interaction Methods (Abstract) ---

    /**
     * FR7: Attempts to equip the item.
     * @param player The Player object.
     * @return true if successfully equipped, false otherwise.
     */
    public abstract boolean equip(Player player);

    /**
     * FR8: Attempts to consume (and delete) the item.
     * @param player The Player object.
     * @return true if successfully consumed, false otherwise.
     */
    public abstract boolean consume(Player player);

    /**
     * FR9: Attempts to use the item without destroying it.
     * @param player The Player object.
     * @return true if successfully used, false otherwise.
     */
    public abstract boolean use(Player player);

    @Override
    public String toString() {
        return name + " (" + itemType + "): " + description;
    }
}