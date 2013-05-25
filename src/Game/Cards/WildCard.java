package Game.Cards;

import Game.Card;
import Game.Card.Color;
import Game.Card.Value;

public class WildCard extends Card{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WildCard(Color col, Value val) {
		super(col, val);
	}
	
	@Override
	public void action() {
		
	}
	
}