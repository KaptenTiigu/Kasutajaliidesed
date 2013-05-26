package Game.Cards;

import Game.Card;
import Game.Card.Color;
import Game.Card.Value;
import Server.ClientSession;


public class NumberCard extends Card {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumberCard(Color color, Value value) {
		super(color, value);
	}

	@Override
	public void action(ClientSession sess) {
		
	}

}

