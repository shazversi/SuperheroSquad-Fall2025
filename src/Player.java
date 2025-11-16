/**
 * Player.java
 * Represents the user-controlled character in the game.
 * Manages inventory, combat actions, and game lifecycle operations.
 * Author: Triniese Thompson
 */
package game.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
     * Prompts the user for their name and creates a Player instance.
     * @return A new Player object with the entered name.
     */
    public static Player createPlayerFromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        player.startGame();
        return player;
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

    // Other methods remain unchanged (checkInventory, pickUpItem, dropItem, etc.)
}
