package background;

import java.awt.Color;

public enum Faction {
	VACANT(Color.WHITE),
	BLOCKED(Color.WHITE),
	BLACK(Color.BLACK),
	RED(Color.RED),
	GREEN(Color.GREEN),
	BLUE(Color.BLUE),
	PINK(Color.PINK);
	
	private Color color;
	
	private Faction(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	
	public static Faction getRandom() {
		Faction t = values()[(int) (Math.random() * values().length)];
		
		while(t == Faction.VACANT || t == Faction.BLOCKED){
			t = values()[(int) (Math.random() * values().length)];
		}
        return t;
    }
}
