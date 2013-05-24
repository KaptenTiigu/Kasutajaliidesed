package Game;

import java.io.Serializable;

public abstract class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public enum Color {
		BLUE, RED, GREEN, YELLOW;
	}
	public enum Value {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, DRAWTWO,
		WILD, WILDDRAWFOUR;		
	}
	
	private Color color;
	private Value value;
	
	public Card(Color col, Value val) {
		this.color = col;
		this.value = val;
	}
	
	public Color getColor() {
		return color;
	}
	public Value getValue(){
		return value;
	}
	public boolean compareCards(Card card, Color col) {
		if (col == null) {
			if (value.compareTo(card.getValue()) == 0 || color.compareTo(card.getColor()) == 0) {
				return true;
			}
			return false;
		} else {
			if (color.compareTo(col) == 0) {
				return true;
			}
		}
		return false;
	}
	public void action() {}
}