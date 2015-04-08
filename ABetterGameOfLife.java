import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import background.*;

@SuppressWarnings("serial")
public class ABetterGameOfLife extends JPanel implements Runnable{
	
	private static final int fps = 30; // max fps
	private static final int dt = 1000 / fps;
	private int width;
	private int height;
	
	
	//Balance vars
	private static final int chanceOfLife = 5;
	private static final int randomGen = 5000;
	private int speed = 1;
	
	//DON'T TOUCH
	private static ABetterGameOfLife me;
	public static JFrame parentWindow;
	
	private BufferedImage background;
	private TileGrid map;

	public static void main(String[] args) {
		
		
		
		parentWindow = new JFrame("A Better Game of Life by Andrew Gleeson");
		parentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		if(args.length != 0){
			me = new ABetterGameOfLife(args[0]);
		}else{
			me = new ABetterGameOfLife("europe_small");
		}
		
		
		parentWindow.getContentPane().add(me);
		parentWindow.setVisible(true);
		
	}
	
	public ABetterGameOfLife(String filename) {
		try{
			File f = new File("img/"+filename+".png");
			if(f.exists()){
				background=ImageIO.read(f);
			}else{
				System.out.println("File does not exist - using europe_small");
				background=ImageIO.read(new File("img/europe_small.png"));
			}
			
		}catch(IOException e){
			System.err.println(e);

		}
		
		width = Math.min(3*background.getWidth(), 1500);
		height = Math.min(3*background.getHeight(), 1000);
		parentWindow.setSize(width, height);

		map = new TileGrid(background, chanceOfLife, randomGen);
		
		new Thread(this).start();
		
	}
	
	
	public void update(){
		for(int i = 0; i < speed; i++)
			map.update();
	}
	
	public void paint(Graphics g1){
		//super.paint(g1);
		
		Image bufferImage = createImage(background.getWidth(), background.getHeight());
		Graphics g = bufferImage.getGraphics();
		
		//draw stuff to buffer

		
		map.draw(g);
		
		Graphics2D g2d = (Graphics2D)g;
		float opacity = 0.5f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(background, 0, 0, null);
		

		BufferedImage after = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(getWidth()*1.0/background.getWidth(), getHeight()*1.0/background.getHeight());
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter( (BufferedImage) bufferImage, after);
		
		//scale and display
		int winWidth = this.getWidth();
		int winHeight = this.getHeight();
		g1.drawImage(after, 0, 0, winWidth, winHeight,  null);
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
