package background;

import java.awt.Graphics;

public class Tile {
	private Faction owner;
	
	private boolean alive;
	
	private Terrain map;
	
	private Tile[] neighbors;
	
	public Tile(Terrain map){
		this.map = map;

		setOwner(Faction.VACANT);
	}
	
	public Tile(Terrain map, Faction owner){
		this.map = map;
		setOwner(owner);
		alive = true;
	}
	
	public void update(){
		//1 - underpopulation
		if(fneighbors() < 2) setStatus(false);
		
		//2 - survive
		// do nothing
		
		//3 - overcrowding
		if(totalNeighbors() > 3) setStatus(false);
		
		//4 - reproduction
		//dead?
		if(!isAlive() && getOwner() != Faction.BLOCKED){
			//has neighbors?
			Faction g = greatestFaction();
			if(g != Faction.VACANT){
				//enough of them?
				if(neighborsOfFaction(g) == 3){
					//it's alive!
					setOwner(g);
					setStatus(true);
				}
			}
		}
		
		//5 - warfare
		if(isAlive() && eneighbors() > fneighbors()){
			setStatus(false);
		}
	}
	
	public void generateNeighbors(int x, int y){
		neighbors = new Tile[8];
		try{
			neighbors[0] = map.map[x-1][y-1];
			neighbors[1] = map.map[x][y-1];
			neighbors[2] = map.map[x+1][y-1];
			neighbors[3] = map.map[x-1][y];
			neighbors[4] = map.map[x+1][y];
			neighbors[5] = map.map[x-1][y+1];
			neighbors[6] = map.map[x][y+1];
			neighbors[7] = map.map[x+1][y+1];
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
	
	public int neighborsOfFaction(Faction f){
		int count = 0;
		for(Tile t : neighbors){
			if(t != null && t.getOwner() == f){
				count++;
			}
		}
		
		return count;
	}
	
	public int eneighbors(){
		int count = 0;
		for(Tile t : neighbors){
			if( enemies(t) ){
				count++;
			}
		}
		
		return count;
	}
	
	public int fneighbors(){
		int count = 0;
		for(Tile t : neighbors){
			if( friends(t) ){
				count++;
			}
		}
		
		return count;
	}
	
	public int totalNeighbors(){
		return eneighbors() + fneighbors();
	}
	
	public Faction greatestFaction(){
		int black = 0;
		int red = 0;
		int green = 0;
		
		for(Tile t : neighbors){
			if(enemies(t)){
				switch(t.getOwner()){
					case BLACK:
						black++;
						break;
					case RED:
						red++;
						break;
					case GREEN:
						green++;
						break;
					default:
						break;
				}
			}
		}
		
		if(black == 0 && red == 0 && green == 0) return Faction.VACANT;
		
		if(Math.max(Math.max(black, red), green) == green){
			return Faction.GREEN;
		}else if(Math.max(black, red) == black){
			return Faction.BLACK;
		}else{
			return Faction.RED;
		}
	}
	
	public boolean friends(Tile t){
		return t == null ? false : getOwner() == t.getOwner();
	}
	
	public boolean enemies(Tile t){
		return t == null ? false : t.getOwner() != this.getOwner() && t.getOwner() != Faction.BLOCKED && t.getOwner() != Faction.VACANT;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void setStatus(boolean alive){
		this.alive = alive;
		if(alive == false && getOwner() != Faction.BLOCKED) setOwner(Faction.VACANT);
	}
	
	public void setOwner(Faction owner){
		if(owner != null){
			this.owner = owner;
		}else{
			this.owner = Faction.getRandom();
		}
	}
	
	public Faction getOwner(){
		return owner;
	}
	
	public void draw(Graphics g, int x, int y){

		if(isAlive())
			g.setColor(getOwner().getColor());
		else{
			g.setColor(getOwner().getColor().brighter().brighter().brighter());
		}
		
		g.fillRect(x, y, 1, 1);
	}
}
