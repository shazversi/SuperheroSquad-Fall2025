import java.io.*;
import java.util.*;

public class GamePersistence {

    /**
     * Save game data to a new file.
     * FR20: If file exists → reject.
     */
    public static boolean saveGame(String filename, List<Monster> monsters) {
        File file = new File(filename);

        if (file.exists()) {
            System.out.println("File name already exists. Try another name.");
            return false;  // FR20 requirement
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write("#MONSTERS");
            out.newLine();

            for (Monster m : monsters) {
                out.write(m.toString());
                out.newLine();
            }

            System.out.println("Game saved successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving file.");
            return false;
        }
    }

    /**
     * Load game data.
     * FR21: If invalid filename → message.
     */
    public static List<Monster> loadGame(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("You have entered an invalid text file name. Please check spelling and try again.");
            return null; // FR21 requirement
        }

        List<Monster> monsters = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = in.readLine(); // should be "#MONSTERS"

            while ((line = in.readLine()) != null) {
                monsters.add(Monster.fromString(line));
            }

            System.out.println("Game loaded successfully.");
            return monsters;

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return null;
        }
    }
}