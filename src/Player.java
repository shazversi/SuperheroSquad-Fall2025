import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Character {

    private Room currentRoom;
    private ArrayList<Item> inventory;
    private Item equippedItem;
    private static final int MAX_INVENTORY_SIZE = 10;

    public Player(String name, int health, int damage) {
        super(name, health, damage);
        this.inventory = new ArrayList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    
    public boolean isAlive() {
    	return this.getHp() > 0;
    }

    // Pickup item from room
    public void pickUpItem(Item item) {
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        if (inventory.size() < MAX_INVENTORY_SIZE) {
            inventory.add(item);
            System.out.println(item.getName() + " added to inventory.");
        } else {
            System.out.println("Inventory full! Cannot pick up " + item.getName());
        }
    }

    // Drop item from inventory
    public void dropItem(String itemName) {
        Item found = null;
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                found = i;
                break;
            }
        }
        if (found != null) {
            inventory.remove(found);
            currentRoom.addItem(found);
            System.out.println(found.getName() + " dropped.");
        } else {
            System.out.println("Item not in inventory.");
        }
    }

    // Equip an item (assume only EquippableItem)
    public void equipItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName) && i instanceof EquippableItem) {
                equippedItem = i;
                System.out.println(i.getName() + " equipped.");
                return;
            }
        }
        System.out.println("Cannot equip " + itemName);
    }

    public void unequipItem() {
        if (equippedItem != null) {
            System.out.println(equippedItem.getName() + " unequipped.");
            equippedItem = null;
        } else {
            System.out.println("No item equipped.");
        }
    }

    public void heal(int amount) {
        hp += amount;
    }

    // Heal using consumable item
    public void healItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName) && i instanceof ConsumableItem) {
                ConsumableItem c = (ConsumableItem) i;
                heal(c.getHealedAmount());
                inventory.remove(i);
                System.out.println("Healed " + c.getHealedAmount() + " HP using " + c.getName());
                return;
            }
        }
        System.out.println("No consumable item named " + itemName);
    }

    // Inspect an item
    public void inspectItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                System.out.println(i.getDescription());
                return;
            }
        }
        System.out.println("Item not in inventory.");
    }

    // Show inventory
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("Inventory:");
        for (Item i : inventory) {
            System.out.println("- " + i.getName());
        }
    }

    // Static scanner for puzzles or monster decisions
    public static String nextLine() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int getDamage() {
        return attack;
    }

    public void takeDamage(int amount) {
        hp = hp - amount;
    }

    public void applyInvisibility(int effectValue) {
    }

    public void markPuzzleBypassAvailable() {
    }

    public Inventory getInventory() {
        return null;
    }

    public boolean hasItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }
    }
