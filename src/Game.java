/*Author: Sean Lor*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {


        private ArrayList<Room> Rooms = new ArrayList<>();
        private Player p;
        private GameView gameview;

        public void start() {
        	Scanner input = new Scanner(System.in);

      
            try {
            	GameFileReader reader = new GameFileReader();
                gameview = new GameView();
            	p = reader.loadPlayer("Player.txt");
				//Player player = reader.loadPlayer("Player.txt");
				//List<Room> rooms = reader.loadRooms("Room.csv");
                Rooms = new ArrayList<>(reader.loadRooms("Room.csv"));
				List<Puzzle> puzzles = reader.loadPuzzles("Puzzle.csv");
				List<Monster> monsters = reader.loadMonsters("monster.txt");
		        List<Item> items = reader.loadItems("Items.txt");
			} catch (Exception e) {
				e.printStackTrace();
			}
            

            System.out.println(
            	    "You're lost. After your submarine crashed, you wake up protected by a huge \n" +
            	    "bubble. And then, you hear a voice...\n" +
            	    "'Find heart. Return balance...'\n" +
            	    "Gloomy statues appear ahead.\n" +
            	    "Move commands: North, East, South, West\n" +
            	    "Other commands: Explore, Pickup [item], Inspect [item], Equip [item], Drop [item], Inventory, Examine [monster], Ignore [monster]\n" +
            	    "Combat commands: Attack, Heal\n" +
            	    "Q to quit\n" +
            	    "H for Help command"
            	);

    //game status
        while (p.getCurrentRoom() != null) {
        System.out.println("You are in Room: " + p.getCurrentRoom().getRoomNumber());
        System.out.println("Exit towards (North, East, South, West)?: ");
  
        String c;
        c = input.nextLine();
        if (c.equalsIgnoreCase("Quit game")) {
            System.out.println("Quit the game successfully.");
            break;
        }
        if (c.equals("Help")) {
            try {
                gameview.Help();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            c = input.nextLine();
        }
        if (c.equalsIgnoreCase("Explore")) {
            //System.out.println(p.getCurrentRoom().explore());
            gameview.roomExplore(p.getCurrentRoom());
        } else if (c.startsWith("Pickup ")) {
            String itemName = c.substring(7).trim();
            Item item = p.getCurrentRoom().findItem(itemName);
            p.pickUpItem(item);
        } else if (c.startsWith("Inspect ")) {
            String itemName = c.substring(8).trim();
            p.inspectItem(itemName);
        } else if (c.startsWith("Drop ")) {
            String itemName = c.substring(5).trim();
            p.dropItem(itemName);
        } else if (c.startsWith("Equip ")) {
            String itemName = c.substring(6).trim();
            p.equipItem(itemName);
        } else if (c.startsWith("Heal ")) {
            String itemName = c.substring(5).trim();
            p.healItem(itemName);
        } else if (c.startsWith("Unequip ")) {
            p.unequipItem();
        } else if (c.equalsIgnoreCase("Inventory")) {
            p.showInventory();
        } else if (c.equalsIgnoreCase("Read Map")) {
            gameview.readMap();
        } else {
            int NextRoomNum = p.getCurrentRoom().getConnectedRoom(c);
            //checks if door isn't there (0)
            if (NextRoomNum == 0) {
                System.out.println("No door there.");
            }
            Room nextRoom = null;
            //checks which room the door leads to
            for (Room a : Rooms) {
                if (a.getRoomNumber() == NextRoomNum) {
                    nextRoom = a;
                }
            }
            if (nextRoom != null) {
                p.setCurrentRoom(nextRoom);
                //checks if room was visited
                if (p.getCurrentRoom().isVisited()) {
                    System.out.println("You've been here before.");
                } else {
                    p.getCurrentRoom().visit();
                }
            }

            //checks if room has a puzzle
            if (p.getCurrentRoom().hasPuzzle()) {
                Puzzle puzzle = p.getCurrentRoom().getPuzzle();
                System.out.println(puzzle.getDescription());

                //int attemptsLeft = puzzle.getMaxAttempts();
                //boolean solved = false;

                //while (attemptsLeft > 0) {
                while (puzzle.attemptPuzzle()) {
                    System.out.println("Enter your answer: ");
                    String answer = input.nextLine();

                    if (puzzle.checkSolution(answer)) {
                    String inputp = Player.nextLine();
                    if (puzzle.isSolved()) {
                        System.out.println("You solved the puzzle correctly!");
                        System.out.println(puzzle.getSuccessMessage());
                        p.getCurrentRoom().removePuzzle();
                        break;

                    } else {
                        //attemptsLeft--;
                        if (puzzle.attemptsLeft() > 0) {
                            System.out.println("The answer you provided is wrong, you still have " + puzzle.attemptsLeft() + " attempts. Try one more time.");
                        } else {
                            System.out.println("Failed to solve.");
                            System.out.println(puzzle.getFailureMessage());
                        }
                    }
                }


                //if (solved) {
                 //   p.getCurrentRoom().removePuzzle(); // Remove puzzle from room
                //}
            }

            if (p.getCurrentRoom().hasMonster()) {
                Monster monster = p.getCurrentRoom().getMonster();
                System.out.println("You encounter a monster: " + monster.getName());
                System.out.println("Do you want to Attack, Examine, or Ignore?");

                String decision = Player.nextLine().trim();

                while (p.getCurrentRoom().hasMonster()) {
                    if (decision.equalsIgnoreCase("Ignore")) {
                        System.out.println("You chose to ignore the monster.");
                        break;
                    } else if (decision.equalsIgnoreCase("Examine")) {
                        System.out.println(monster.getDescription());
                        decision = Player.nextLine().trim();
                    } else if (decision.equalsIgnoreCase("Attack")) {
                        startCombat(p, monster, input);
                    } else {
                        System.out.println("Invalid choice. Attack or Ignore:");
                        //c = Player.nextLine();
                        c = input.nextLine();

                    }
                }
            }
        }
        }


    }

    public void startCombat(Player player, Monster monster, Scanner inputScanner) {
        System.out.println("Combat started with " + monster.getName() + "!");

        while (player.isAlive() && !monster.isDead()) {
            // Player's turn
            System.out.println("\nYour HP: " + player.getHp() + " | Monster HP: " + monster.getHitPoints());
            System.out.println("Choose your action: Attack, Heal [item]");

            String action = inputScanner.nextLine().trim();

            if (action.equalsIgnoreCase("Attack")) {
                int damage = player.getDamage();
                monster.takePlayerAttack(damage);
                System.out.println("You attacked " + monster.getName() + " for " + damage + " damage.");
            } else if (action.startsWith("Heal ")) {
                String itemName = action.substring(5).trim();
                player.healItem(itemName);
            } else if (action.startsWith("Equip ")) {
                String itemName = action.substring(6).trim();
                player.equipItem(itemName);
            } else if (action.equalsIgnoreCase("Unequip")) {
                player.unequipItem();
            } else {
                System.out.println("Invalid action. Try again.");
                continue;
            }

            // Check if monster.txt is defeated
            if (monster.isDead()) {
                System.out.println("You defeated the monster!");
                player.getCurrentRoom().removeMonster();
                break;
            }

            // Monster's turn
            int monsterDamage = monster.attackPlayer();
            player.takeDamage(monsterDamage);
            System.out.println(monster.getName() + " attacked you for " + monsterDamage + " damage.");

            // Check if player is defeated
            if (!player.isAlive()) {
                System.out.println("You were defeated by the monster...");
                System.out.println("Choose: Q or Restart");
                String choice = inputScanner.nextLine().trim();
                if (choice.equalsIgnoreCase("Restart")) {
                    System.out.println("Restarting game...");
                    start(); // restart the game
                    return;
                } else if (choice.equalsIgnoreCase("Q")) {
                    System.out.println("Quit the game successfully.");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Quiting.");
                    System.exit(0);
                }
            }
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
