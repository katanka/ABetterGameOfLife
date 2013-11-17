package background;

import java.awt.Graphics;

public class Tile {
	private Team owner;
	
	private boolean alive;
	
	private Terrain map;
	
	public Tile(Terrain map){
		this.map = map;

		setOwner(Team.VACANT);
	}
	
	public Tile(Terrain map, Team owner){
		this.map = map;
		setOwner(owner);
		alive = true;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void setStatus(boolean alive){
		this.alive = alive;
	}
	
	public void setOwner(Team owner){
		if(owner != null){
			this.owner = owner;
		}else{
			this.owner = Team.getRandom();
		}
	}
	
	public Team getOwner(){
		return owner;
	}
	
	public void draw(Graphics g, int x, int y){
		g.setColor(getOwner().getColor());
		if(isAlive())
			g.fillRect(x, y, 1, 1);
	}
}
