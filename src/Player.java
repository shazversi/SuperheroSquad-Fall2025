/**
 * Player.java
 * Represents the user-controlled character in the game.
 * Manages inventory, combat actions, and game lifecycle operations.
 * Author: Triniese Thompson
 */
package game.model;

import java.io.*;
import java.util.ArrayList;

/**
 * The Player class extends Character and adds functionality for inventory management,
 * combat actions, and game lifecycle operations such as saving and loading progress.
 */
public class Player extends Character {
    /** Inventory of items represented as strings */
    private ArrayList<String> inventory;
    /** Maximum number of items allowed in inventory */
    private final int MAX_INVENTORY_SIZE = 10;

    /**
     * Constructs a Player with a given name and default stats.
     * @param name The name of the player.
     */
    public Player(String name) {
        super(name, 50, 0); // Default HP = 50, Attack = 0
        this.inventory = new ArrayList<>();
    }

    /** Starts the game and welcomes the player */
    public void startGame() {
        System.out.println("Welcome to Lost in Atlantis, " + name + "! Your journey begins.");
    }

    /**
     * Saves the player's current state to a file.
     * @param filename The name of the file to save.
     */
    public void saveGame(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(name + "
");
            writer.write(hp + "
");
            writer.write(attack + "
");
            for (String item : inventory) {
                writer.write(item + ",");
            }
            writer.newLine();
            System.out.println("Game saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads the player's state from a file.
     * @param filename The name of the file to load.
     */
    public void loadGame(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.name = reader.readLine();
            this.hp = Integer.parseInt(reader.readLine());
            this.attack = Integer.parseInt(reader.readLine());
            String itemsLine = reader.readLine();
            inventory.clear();
            if (itemsLine != null && !itemsLine.isEmpty()) {
                for (String item : itemsLine.split(",")) {
                    if (!item.isBlank()) inventory.add(item);
                }
            }
            System.out.println("Game loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

    /** Quits the game */
    public void quitGame() {
        System.out.println("Thank you for playing Lost in Atlantis!");
    }

    /** Displays the player's inventory and remaining space */
    public void checkInventory() {
        System.out.println("Inventory: " + inventory);
        System.out.println("Space left: " + (MAX_INVENTORY_SIZE - inventory.size()));
    }

    /** Adds an item to the inventory if space is available */
    public void pickUpItem(String itemName) {
        if (inventory.size() < MAX_INVENTORY_SIZE) {
            inventory.add(itemName);
            System.out.println(itemName + " added to inventory.");
        } else {
            System.out.println("Inventory full! Cannot pick up " + itemName);
        }
    }

    /** Removes an item from the inventory */
    public void dropItem(String itemName) {
        if (inventory.remove(itemName)) {
            System.out.println(itemName + " dropped.");
        } else {
            System.out.println(itemName + " not found in inventory.");
        }
    }

    /** Equips an item and adjusts attack power if applicable */
    public void equipItem(String itemName) {
        if (inventory.contains(itemName)) {
            System.out.println("Equipped " + itemName);
            if (itemName.toLowerCase().contains("sword")) attack += 10;
            else if (itemName.toLowerCase().contains("spear")) attack += 15;
        } else {
            System.out.println(itemName + " not in inventory.");
        }
    }

    /** Consumes an item and applies its effect */
    public void consumeItem(String itemName) {
        if (inventory.remove(itemName)) {
            System.out.println("Consumed " + itemName);
            if (itemName.toLowerCase().contains("health potion")) hp = Math.min(50, hp + 20);
        } else {
            System.out.println(itemName + " not in inventory.");
        }
    }

    /** Uses an item without removing it */
    public void useItem(String itemName) {
        if (inventory.contains(itemName)) {
            System.out.println("Used " + itemName);
        } else {
            System.out.println(itemName + " not in inventory.");
        }
    }

    /** Attacks a monster using inherited attack method */
    public void attackMonster(Character monster) {
        super.attack(monster);
    }

    /** Defends against an attack */
    public void defend() {
        System.out.println("Defending! Incoming damage will be reduced.");
    }

    /** Attempts to flee from combat */
    public void flee() {
        System.out.println("Attempting to flee...");
    }

    /** Displays the player's current stats */
    public void checkPlayerStats() {
        System.out.println("Name: " + name + ", HP: " + hp + ", Attack: " + attack);
    }
}
