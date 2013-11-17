package display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import background.*;

public class GameOfLifeDisplay extends JPanel implements Runnable{
	
	private static final int fps = 2;
	private static final int dt = 1000 / fps;
	private static int WIDTH;
	private static int HEIGHT;
	
	//Balance vars
	private static final int chanceOfLife = 2;
	private static final int populate = 3;
	
	private static GameOfLifeDisplay me;
	public static JFrame parentWindow;
	
	private BufferedImage background;
	private Terrain map;

	public static void main(String[] args) {
		
		
		
		parentWindow = new JFrame("Conway's Game of Life, Mapped - pre-alpha");
		parentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		me = new GameOfLifeDisplay();
		
		parentWindow.getContentPane().add(me);
		parentWindow.setVisible(true);
		
	}
	
	public GameOfLifeDisplay() {
		try{
			background=ImageIO.read(new File("background.png"));
		}catch(IOException e){
			System.err.println(e);
		}
		parentWindow.setSize(background.getWidth(), background.getHeight());
		
		map = new Terrain(background, chanceOfLife);
		
		new Thread(this).start();
		
	}
	
	
	public void update(){
		map.update();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		g.drawImage(background, 0, 0, null);
		
		map.draw(g);
		
	}
	
	
	public void run(){
		long tm = System.currentTimeMillis();
		while(true){
            update();
            repaint();
            
            try {
                tm += dt;
                Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
            }
            catch(InterruptedException e)
            {
            	System.err.println(e);
            }
        }
	}

}
