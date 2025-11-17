

//room.csv -> zone,room number,false,name,description,exits
//north-east-south-west

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private int zone;
    private int roomNumber;
    private String name;
    private String description;
    private Map<String, Integer> exits;
    private boolean visited;
    private ArrayList<Item> items = new ArrayList<>();
    private Puzzle puzzle;
    private boolean puzzleSolved = false;
    private Monster monster;
    private boolean monsterDefeat = false;
    private boolean hasPath;
    private boolean hasTransportMechanism;
    private int pathDestination;
    private int transportDestination;
    private String puzzleID;

    //constructor
    public Room(int zone, int roomNumber, String name, String description) {
        this.zone = zone;
        this.roomNumber = roomNumber;
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.visited = false;
        this.items = new ArrayList<Item>();
    }

    //getters
    public int getZone() {
        return zone;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    public boolean isVisited() {
        return visited;
    }

    //method to add an exit
    public void addExit(String direction, int roomNumber) {
        exits.put(direction, roomNumber);
    }

    public void visit() {
        visited = true;
    }

    public int getConnectedRoom(String direction) {
        Integer roomNum = exits.get(direction);
        if (roomNum == null) {
            return 0;
        }
        return roomNum;
    }

    public void addItem(Item a) {
        items.add(a);
    }

    public Item removeItem(String b) {
        for (Item a : items) {
            if (a.getName().equals(b)) {
                items.remove(a);
                return a;
            }
        }
        return null;
    }

    public boolean hasPuzzle() {
        return puzzle != null;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void markPuzzleSolved() {
        puzzleSolved = true;
    }

    public boolean hasMonster() {
        return monster != null && !monster.isDead();
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }

    public void markMonsterDefeat() {
        monsterDefeat = true;
    }

    public void removeMonster() {
        this.monster = null;
    }
    //PATH GETTERS AND SETTERS
    public boolean hasPath() {
        return hasPath;
    }

    public void setHasPath(boolean hasPath) {
        this.hasPath = hasPath;
    }

    public int getPathDestination() {
        return pathDestination;
    }

    public void setPathDestination(int pathDestination) {
        this.pathDestination = pathDestination;
    }

    //TRANSPORT GETTERS AND SETTERS
    public boolean hasTransportMechanism() {
        return hasTransportMechanism;
    }

    public void setHasTransportMechanism(boolean hasTransportMechanism) {
        this.hasTransportMechanism = hasTransportMechanism;
    }

    public int getTransportDestination() {
        return transportDestination;
    }

    public void setTransportDestination(int transportDestination) {
        this.transportDestination = transportDestination;
    }

    public String getPuzzleID() {
        return puzzleID;
    }

    public void setPuzzleID(String puzzleID) {
        this.puzzleID = puzzleID;
    }
}