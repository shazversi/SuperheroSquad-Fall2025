/*Author: Sean Lor*/

public class FileReader {

    public static Player loadPlayer(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] parts = line.split(",");
            return new Player(parts[0].trim(), Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()));
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
                            Boolean.parseBoolean(parts[2].trim()),
                            parts[3].trim(),
                            parts[4].trim(),
                            Integer.parseInt(parts[5].trim()),
                            Integer.parseInt(parts[6].trim()),
                            Integer.parseInt(parts[7].trim()),
                            Integer.parseInt(parts[8].trim())
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
                if (parts.length >= 8) {
                    puzzles.add(new Puzzle(
                            parts[0].trim(),
                            Integer.parseInt(parts[1].trim()),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].trim(),
                            parts[5].trim(),
                            parts[6].trim(),
                            parts[7].trim()
                    ));
                }
            }
        }
        return puzzles;
    }

}
