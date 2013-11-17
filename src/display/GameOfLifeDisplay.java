package display;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import background.*;

public class GameOfLifeDisplay extends JPanel implements Runnable{
	
	private static final int fps = 30; // max fps
	private static final int dt = 1000 / fps;
	private int width;
	private int height;
	
	
	//Balance vars
	private static final int chanceOfLife = 4;
	private int speed = 1;
	
	//DON'T TOUCH
	private static GameOfLifeDisplay me;
	public static JFrame parentWindow;
	
	private BufferedImage background;
	private TileGrid map;

	public static void main(String[] args) {
		
		
		
		parentWindow = new JFrame("Conway's Game of Life, Mapped - beta");
		parentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		me = new GameOfLifeDisplay();
		
		parentWindow.getContentPane().add(me);
		parentWindow.setVisible(true);
		
	}
	
	public GameOfLifeDisplay() {
		try{
			background=ImageIO.read(new File("img/europe_small.png"));
		}catch(IOException e){
			System.err.println(e);
		}
		
		width = background.getWidth();
		height = background.getHeight();
		parentWindow.setSize(width, height);
		
		map = new TileGrid(background, chanceOfLife);
		
		new Thread(this).start();
		
	}
	
	
	public void update(){
		for(int i = 0; i < speed; i++)
			map.update();
	}
	
	public void paint(Graphics g1){
		super.paint(g1);
		
		Image bufferImage = createImage(width, height);
		Graphics g = bufferImage.getGraphics();
		
		//draw stuff to buffer
		g.drawImage(background, 0, 0, null);
		map.draw(g);
		
		//scale and display
		int winWidth = this.getWidth();
		int winHeight = this.getHeight();
		g1.drawImage(bufferImage, 0, 0, winWidth, winHeight,  null);
		
		
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
