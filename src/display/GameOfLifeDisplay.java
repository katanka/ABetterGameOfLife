package display;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameOfLifeDisplay extends JPanel{

	public static void main(String[] args) {
		JFrame window = new JFrame("Conway's Game of Life, Mapped - pre-alpha");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(new GameOfLifeDisplay());
		window.setVisible(true);
		
	}

}
