package background;

import java.awt.Color;

public enum Terrain {
	NORMAL(0, Color.WHITE),
	MOUNTAINS(0.1, Color.RED),
	WATER(0.4, Color.BLUE),
	BLOCKED(1, Color.BLACK);
	
	private double chance;
	private Color color;
	
	private Terrain(double chanceOfDeath, Color color){
		chance = chanceOfDeath;
		this.color = color;
	}
	
	public double getChance(){
		return chance;
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean tryKill(){
		return Math.random() < getChance();
	}
}
