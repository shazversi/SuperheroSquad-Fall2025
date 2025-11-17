/*Author: Sean Lor*/

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;

public class GameFileReader {

    public static Player loadPlayer(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] parts = line.split(",");
            return new Player(parts[0].trim());
        }
    }

    public static List<Room> loadRooms(String filePath) throws IOException {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    rooms.add(new Room(
                            Integer.parseInt(parts[0].trim()),
                            Integer.parseInt(parts[1].trim()),
                            parts[3].trim(),
                            parts[4].trim()
                    ));
                }
            }
        }
        return rooms;
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
            // Skip header lines
            for (int i = 0; i < 7; i++) br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s{2,}|\\t"); // Split by multiple spaces or tabs
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0].trim());
                    String roomLocation = parts[1].trim();
                    String name = parts[2].trim();
                    String type = parts[3].trim();
                    String effect = parts[4].trim();
                    String description = parts[5].trim();
                    items.add(new Item(id, roomLocation, name, type, effect, description));
                }
            }
        }
        return items;
    }


}
