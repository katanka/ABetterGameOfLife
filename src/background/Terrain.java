package background;

import java.awt.image.BufferedImage;

public class Terrain {
	
	private Tile[][] map;
	
	public Terrain(BufferedImage img){
		map = new Tile[img.getWidth()/3][img.getHeight()/3];
	}
	
}
