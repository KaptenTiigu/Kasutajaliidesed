package Game;

public interface Card {
	
	enum Color {
		BLUE, RED, GREEN, YELLOW;
	}
	enum Value {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, DRAWONE, DRAWTWO,
		WILD, WILDDRAWFOUR;		
	}
	
	Color getColor();
	Value getValue();
	boolean compareCards(Card card, Color color);
	void action();
}
