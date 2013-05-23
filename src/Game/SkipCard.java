package Game;

import Game.Card.Color;
import Game.Card.Value;

public class SkipCard implements Card {
	
	private Color color;
	private Value value;
	
	public SkipCard(Color color, Value value) {
		this.color = color;
		this.value = value;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
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

	@Override
	public void action() {
		
	}
}
