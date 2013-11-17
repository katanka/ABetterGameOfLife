package background;

import java.awt.Graphics;

public class Tile {
	private Team owner;
	
	private Terrain map;
	
	private int x, y;
	
	public Tile(Terrain map, int x, int y){
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public Team getOwner(){
		return owner;
	}
	
	public void draw(Graphics g){
		g.setColor(owner.getColor());
		g.fillRect(x, y, 3, 3);
	}
}
