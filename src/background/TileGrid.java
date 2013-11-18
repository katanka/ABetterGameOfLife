package background;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

public class TileGrid {
	
	public Tile[][] map;
	
	private int chanceOfLife;
	private int randomGen;
	
	private BufferedImage img;
	
	public TileGrid(BufferedImage img, int chance, int randomGen){
		
		map = new Tile[img.getWidth()][img.getHeight()];
		
		chanceOfLife = chance;
		
		this.randomGen = randomGen;
		
		generateFromImg(img);
		
		this.img = img;
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
				
				map[x][y].update();
				
				
				/*if(map[x][y].getOwner() == Faction.VACANT){
					if (Math.floor(Math.random() * randomGen) < 1) {
						map[x][y] = new Tile(this, null, getTerrainFromImage(img, x, y)); //random faction
					}
				}*/
			}	
		}
	}
	
	private Terrain getTerrainFromImage(BufferedImage img, int x, int y){
		Terrain t = null;
		
		if(img.getRGB(x, y) == Terrain.NORMAL.getColor().getRGB()){
			t = Terrain.NORMAL;
		}else if(img.getRGB(x, y) == Terrain.MOUNTAINS.getColor().getRGB()){
			t = Terrain.MOUNTAINS;
		}else if(img.getRGB(x, y) == Terrain.WATER.getColor().getRGB()){
			t = Terrain.WATER;
		}else{
			t = Terrain.BLOCKED;
		}
		
		return t;
	}
	
	//Generate terrain from input image
	private void generateFromImg(BufferedImage img){
		
		int width = map.length;
		int height = map[0].length;
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				if(img.getRGB(x, y) == Color.BLACK.getRGB()){
					map[x][y] = new Tile(this, Faction.BLOCKED, Terrain.BLOCKED);
				}else{
					
					if (Math.floor(Math.random() * chanceOfLife) < 1) {
						map[x][y] = new Tile(this, null, getTerrainFromImage(img, x, y)); //random faction
					} else {
						map[x][y] = new Tile(this, Faction.VACANT, getTerrainFromImage(img, x, y)); //vacant
					}
					
					
				}
				
			}
		}
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[0].length; y++){
				
				Tile me = map[x][y];
				me.generateNeighbors(x, y);
				me.update();
				
			}	
		}
		
	}
	
}
