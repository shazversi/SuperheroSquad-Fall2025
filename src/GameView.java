import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.io.File;
import java.io.FileNotFoundException;

public class GameView {

	//"Help" command, list of commands
	public void Help() throws IOException {
		String file = "UserManual.txt";
		try (Scanner scanner = new Scanner(new File(file))){
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file);
			e.printStackTrace();
		}
	}
	
	//"Read map" command, displays map on jframe
	public static void readMap() {
		JFrame frame = new JFrame("Map display");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//read png file from src folder
		ImageIcon imageIcon = new ImageIcon(GameView.class.getResource("/LostinAtlantisMap.PNG"));
		JLabel label = new JLabel(imageIcon);
		frame.add(label);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
}
