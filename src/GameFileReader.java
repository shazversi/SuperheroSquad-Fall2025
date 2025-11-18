/*Author: Sean Lor*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class GameFileReader {

    public Player loadPlayer(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] parts = line.split(",");
            String name = parts[0].trim();
            int health = Integer.parseInt(parts[1].trim());
            int damage = Integer.parseInt(parts[2].trim());
            return new Player(name, health, damage);
        }
    }


    public static ArrayList<Room> loadRooms(String filename) {
        //return all rooms
        ArrayList<Room> all = new ArrayList<>();
        try (
                //reading the csv file
                Scanner in = new Scanner(new FileInputStream(filename));
        ) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");

                int zone = Integer.parseInt(parts[0]);
                int num = Integer.parseInt(parts[1]);
                boolean visited = Boolean.parseBoolean(parts[2]);
                String name = parts[3];
                String description = parts[4];

                //connections
                int north = Integer.parseInt(parts[5]);
                int east = Integer.parseInt(parts[6]);
                int south = Integer.parseInt(parts[7]);
                int west = Integer.parseInt(parts[8]);
                //new room object
                Room a = new Room(zone, num, name, description);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return all;
    }


    public static List<Puzzle> loadPuzzles(String filePath) throws IOException {
        List<Puzzle> puzzles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    puzzles.add(new Puzzle(
                            parts[0].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[6].trim(),
                            Integer.parseInt(parts[4].trim())
                    ));
                }
            }
        }
        return puzzles;
    }

    public static List<Monster> loadMonsters(String filePath) throws IOException {
        List<Monster> monsters = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String name;
            while ((name = br.readLine()) != null) {
                name = name.replace("\"", "").trim();
                String description = br.readLine().replace("\"", "").trim();
                String dropItem = br.readLine().replace("\"", "").trim();
                int attack = Integer.parseInt(br.readLine().trim());
                int defense = Integer.parseInt(br.readLine().trim());
                int hp = Integer.parseInt(br.readLine().trim());
                monsters.add(new Monster(name, description, dropItem, attack, defense, hp));
            }
        }
        return monsters;
    }

    public static List<Item> loadItems(String filePath) throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header lines first 2 lines
            br.readLine(); 
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                // Split by pipe and trim spaces
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                String roomLocation = parts[1].trim();
                String name = parts[2].trim();
                String type = parts[3].trim().toLowerCase();
                String effect = parts[4].trim();
                String description = parts[5].trim();

                switch (type) {
                    case "equip able":
                        // Parse effect for damage/reduction
                        int damage = 0;
                        int reduction = 0;
                        if (effect.toLowerCase().contains("deal damage")) {
                            damage = Integer.parseInt(effect.replaceAll("\\D+", ""));
                        } else if (effect.toLowerCase().contains("reduce monster attacks")) {
                            reduction = Integer.parseInt(effect.replaceAll("\\D+", ""));
                        }
                        items.add(new EquippableItem(name, description, damage, reduction));
                        break;
                    case "consumable":
                        // Example: Heal, invisibility, bypass
                        String effectType = "HEAL"; // default
                        int value = 0;
                        if (effect.toLowerCase().contains("increase")) {
                            value = Integer.parseInt(effect.replaceAll("\\D+", ""));
                            effectType = "HEAL";
                        } else if (effect.toLowerCase().contains("invisible")) {
                            effectType = "INVISIBILITY";
                            value = 2; // default turns
                        } else if (effect.toLowerCase().contains("skip")) {
                            effectType = "BYPASS_PUZZLE";
                        }
                        items.add(new ConsumableItem(name, description, effectType, value));
                        break;
                    case "useable":
                        items.add(new UseableItem(name, description, name.toUpperCase().replaceAll(" ", "_")));
                        break;
                    default:
                        System.out.println("Unknown item type: " + type);
                }
            }
        }
        return items;
    }


}
