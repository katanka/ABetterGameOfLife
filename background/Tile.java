package background;

import java.awt.Graphics;
import java.util.*;
import java.util.Map.*;

public class Tile {
	private Faction owner;
	
	private boolean alive;
	
	private TileGrid map;
	
	private Tile[] neighbors;
	
	private Terrain terrain;
	
	private int x, y;
	
	public Tile(TileGrid map){
		this.map = map;

		setOwner(Faction.VACANT);
		setTerrain(Terrain.NORMAL);
	}
	
	public Tile(TileGrid map, Faction owner){
		this.map = map;
		setOwner(owner);
		alive = true;
		setTerrain(Terrain.NORMAL);
	}
	
	public Tile(TileGrid map, Faction owner, Terrain terrain){
		this.map = map;
		setOwner(owner);
		alive = true;
		setTerrain(terrain);
	}
	
	public void update(){
		
		if(getOwner() == Faction.BLOCKED) return;
		
		//resolveNeighbors(x, y);
		
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
		
		//6 - death by terrain
		if(isAlive()){
			if(getTerrain().tryKill()){
				setStatus(false);
			}
		}
	}
	
	private void resolveNeighbors(int x, int y){
		
		if(neighbors == null){
			generateNeighbors(x, y);
			return;
		}
		
		boolean ok = true;
		for(int i = 0; i < neighbors.length; i++){
			if(neighbors[i] == null){
				ok = false;
				break;
			}
		}
		
		if(ok) return;
		
		generateNeighbors(x, y);
	}
	
	public void generateNeighbors(int x, int y){
		this.x = x;
		this.y = y;
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
		
		for(int i = 0; i < neighbors.length; i++){
			if(neighbors[i] == null){
				neighbors[i] = new Tile(map, Faction.BLOCKED);
			}
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
		
		Map<Faction, Integer> map = new HashMap<Faction, Integer>();
		for (Tile t : neighbors) {
		    Integer count = map.get(t.getOwner());
		    map.put(t.getOwner(), count != null ? count+1 : 0);
		}
		
		map.put(Faction.VACANT, 0);
		
		Faction popular = Collections.max(map.entrySet(),
			    new Comparator<Map.Entry<Faction, Integer>>() {
			    @Override
			    public int compare(Entry<Faction, Integer> o1, Entry<Faction, Integer> o2) {
			        return o1.getValue().compareTo(o2.getValue());
			    }
			}).getKey();
		
		
		return popular;
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
	
	public void setTerrain(Terrain t){
		this.terrain = t;
	}
	
	public Terrain getTerrain(){
		return terrain;
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
