package background;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

public class Terrain {
	
	private Tile[][] map;
	
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
				int fneighbors = 0;
				int eneighbors = 0;
				
				Tile me = map[x][y];
				
				try{
					if(map[x-1][y-1].isAlive()){
						if(map[x-1][y-1].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x-1][y-1].getOwner() != Team.BLOCKED && map[x-1][y-1].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x][y-1].isAlive()){
						if(map[x][y-1].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x][y-1].getOwner() != Team.BLOCKED && map[x][y-1].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x+1][y-1].isAlive()){
						if(map[x+1][y-1].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x+1][y-1].getOwner() != Team.BLOCKED && map[x+1][y-1].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x-1][y].isAlive()){
						if(map[x-1][y].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x-1][y].getOwner() != Team.BLOCKED && map[x-1][y].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x+1][y].isAlive()){
						if(map[x+1][y].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x+1][y].getOwner() != Team.BLOCKED && map[x+1][y].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x-1][y+1].isAlive()){
						if(map[x-1][y+1].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x-1][y+1].getOwner() != Team.BLOCKED && map[x-1][y+1].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x][y+1].isAlive()){
						if(map[x][y+1].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x][y+1].getOwner() != Team.BLOCKED && map[x][y+1].getOwner() != Team.VACANT ) eneighbors++;
					}
					if(map[x+1][y+1].isAlive()){
						if(map[x+1][y+1].getOwner() == me.getOwner()) fneighbors++;
						else if( map[x+1][y+1].getOwner() != Team.BLOCKED && map[x+1][y+1].getOwner() != Team.VACANT ) eneighbors++;
					}
				}catch(ArrayIndexOutOfBoundsException e){
					
				}
				
				
				//1 - underpopulation
				if(map[x][y].isAlive() && fneighbors < 2) map[x][y].setStatus(false);
				
				//2 survival
				// do nothing
				
				//3 - overcrowding
				if(map[x][y].isAlive()){
					if(fneighbors + eneighbors > 3) map[x][y].setStatus(false);
					//enemies
				}
				
				//4 - birth
				if(map[x][y].getOwner() != Team.BLOCKED){
					if(fneighbors + eneighbors == 3) map[x][y].setStatus(true);
				}
				
				
			}	
		}
	}
	
	private void generateFromImg(BufferedImage img){
		
		int width = map.length;
		int height = map[0].length;
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				if(img.getRGB(x, y) == Color.BLACK.getRGB()){
					map[x][y] = new Tile(this, Team.BLOCKED);
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
