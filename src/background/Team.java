package background;

import java.awt.Color;

public enum Team {
	VACANT(Color.WHITE),
	BLOCKED(Color.WHITE),
	BLACK(Color.BLACK),
	RED(Color.RED),
	GREEN(Color.GREEN),
	BLUE(Color.BLUE),
	PINK(Color.PINK),
	YELLOW(Color.YELLOW);
	
	private Color color;
	
	private Team(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	
	public static Team getRandom() {
		Team t = values()[(int) (Math.random() * values().length)];
		
		while(t == Team.VACANT || t == Team.BLOCKED){
			t = values()[(int) (Math.random() * values().length)];
		}
        return t;
    }
}
