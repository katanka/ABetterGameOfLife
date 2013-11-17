package background;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

public class Terrain {
	
	public Tile[][] map;
	
	private int chanceOfLife;
	
	public Terrain(BufferedImage img, int chance){
		
		map = new Tile[img.getWidth()][img.getHeight()];
		
		chanceOfLife = chance;
		
		generateFromImg(img);
	}
	
	public void draw(Graphics g){
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[0].length; y++){
				map[x][y].draw(g, x, y);
			}
		}
	}
	
	public void update(){
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[0].length; y++){
				
				Tile me = map[x][y];
				me.generateNeighbors(x, y);
				me.update();
				
			}	
		}
	}
	
	private void generateFromImg(BufferedImage img){
		
		int width = map.length;
		int height = map[0].length;
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				if(img.getRGB(x, y) == Color.BLACK.getRGB()){
					map[x][y] = new Tile(this, Faction.BLOCKED);
				}else{
					if (Math.floor(Math.random() * chanceOfLife) < 1) {
						map[x][y] = new Tile(this, null);
					} else {
						map[x][y] = new Tile(this);
					}
				}
				
			}
		}
		
	}
	
}
