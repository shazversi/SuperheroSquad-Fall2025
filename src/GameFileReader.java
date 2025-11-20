/*Author: Sean Lor*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class GameFileReader {

   public static Player loadPlayer(String filename) {
       try (Scanner in = new Scanner(new FileInputStream(filename))) {
           while (in.hasNextLine()) {
               String line = in.nextLine();
               String[] parts = line.split(",");
               String name = parts[0].trim();
               int healthPoints = Integer.parseInt(parts[1].trim());
               int attackPoints = Integer.parseInt(parts[2].trim());
               return new Player(name, healthPoints, attackPoints);
           }
       } catch (Exception e) {
           System.out.println(e);
       }
       return null;
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
                int roomNumber = Integer.parseInt(parts[1]);
                boolean visited = Boolean.parseBoolean(parts[2]);
                String name = parts[3];
                String description = parts[4];

                //connections
                int north = Integer.parseInt(parts[5]);
                int east = Integer.parseInt(parts[6]);
                int south = Integer.parseInt(parts[7]);
                int west = Integer.parseInt(parts[8]);
                //new room object
                Room a = new Room(zone, roomNumber, visited, name, description, north, east, south, west);
                a.visited = visited;
                if (north != 0) a.addExit("north", north);
                if (east != 0) a.addExit("east", east);
                if (south != 0) a.addExit("south", south);
                if (west != 0) a.addExit("west", west);
                all.add(a);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return all;
    }


   /* public static List<Puzzle> loadPuzzles(String filePath) throws IOException {
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

    */

    public static ArrayList<Puzzle> loadPuzzles(String filename, ArrayList<Room> r) {
        //return all rooms
        ArrayList<Puzzle> puzzles = new ArrayList<>();
        try (
                //reading the csv file
                Scanner in = new Scanner(new FileInputStream(filename));
        ) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");

                String puzzleID = parts[0].trim();
                int roomNum = Integer.parseInt(parts[1].trim());
                String name = parts[2].trim();
                String description = parts[3].trim();
                int maxAttempts = Integer.parseInt(parts[4]);
                String solutionDescription = parts[5].trim();
                String answer = parts[6].trim();
                String successMessage = parts[7].trim();
                String failureMessage = parts[8].trim();

                Puzzle puzzle = new Puzzle(puzzleID, roomNum, name, description, maxAttempts, solutionDescription, answer, successMessage, failureMessage);
                for (Room room : r) {
                    if (room.getRoomNumber() == roomNum) {
                        room.setPuzzle(puzzle);
                        break;
                    }
                }
                puzzles.add(puzzle);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return puzzles;
    }



/*
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
*/

    public static List<Monster> loadMonsters(String filePath, ArrayList<Room> r) {
        List<Monster> monsters = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // skip blank lines

                // Split by pipe character
                String[] parts = line.split("\\|");
                if (parts.length < 6) {
                    System.out.println("Invalid monster line: " + line);
                    continue;
                }

                String name = parts[0].trim();
                String description = parts[1].trim();
                String dropItem = parts[2].trim();
                int roomId = Integer.parseInt(parts[3].trim());
                int atk = Integer.parseInt(parts[4].trim());
                int hp = Integer.parseInt(parts[5].trim());

                Monster monster = new Monster(name, description, dropItem, roomId, atk, hp);
                for (Room room : r) {
                    if (room.getRoomNumber() == roomId) {
                        room.setMonster(monster);
                        break;
                    }
                }
                monsters.add(monster);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return monsters;
    }

   /* public static List<Item> loadItems(String filePath) throws IOException {
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

                int roomLocation = Integer.parseInt(parts[1].trim());
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
*/
    public static List<Item> loadItems(String filePath, List<Room> rooms) throws IOException {
        List<Item> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                // Split by pipe
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                // Parse fields
                int id = Integer.parseInt(parts[0].trim());         // Item ID
                String roomLocation = parts[1].trim();              // Can be "N/A" or comma-separated room numbers
                String name = parts[2].trim();
                String type = parts[3].trim().toLowerCase();
                String effect = parts[4].trim();
                String description = parts[5].trim();

                Item item = null;

                // Create the correct Item subclass
                switch (type) {
                    case "equipable":
                        int damage = 0;
                        int reduction = 0;
                        if (effect.toLowerCase().contains("deal damage")) {
                            damage = Integer.parseInt(effect.replaceAll("\\D+", ""));
                        } else if (effect.toLowerCase().contains("reduce monster attacks")) {
                            reduction = Integer.parseInt(effect.replaceAll("\\D+", ""));
                        }
                        item = new EquippableItem(name, description, damage, reduction);
                        break;

                    case "consumable":
                        String effectType = "HEAL";
                        int value = 0;
                        if (effect.toLowerCase().contains("increase")) {
                            value = Integer.parseInt(effect.replaceAll("\\D+", ""));
                            effectType = "HEAL";
                        } else if (effect.toLowerCase().contains("invisible")) {
                            effectType = "INVISIBILITY";
                            value = 2;
                        } else if (effect.toLowerCase().contains("skip")) {
                            effectType = "BYPASS_PUZZLE";
                        }
                        item = new ConsumableItem(name, description, effectType, value);
                        break;

                    case "useable":
                        String actionCode = name.toUpperCase().replaceAll(" ", "_");
                        item = new UseableItem(name, description, actionCode);
                        break;

                    default:
                        System.out.println("Unknown item type: " + type);
                        continue; // skip unknown types
                }

                // Add item to master list
                items.add(item);

                // Assign item to rooms if roomLocation is numeric
                if (!roomLocation.startsWith("N/A")) {
                    String[] roomNumbers = roomLocation.split(",");
                    for (String rn : roomNumbers) {
                        int roomNum = Integer.parseInt(rn.trim());
                        Room room = findRoomByNumber(rooms, roomNum);
                        if (room != null) {
                            room.addItem(item); // ✅ add the single item to the room
                        }
                    }
                }
            }
        }

        return items;
    }

    // Helper method to find a room by its number
    private static Room findRoomByNumber(List<Room> rooms, int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }



}
