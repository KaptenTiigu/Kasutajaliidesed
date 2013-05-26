package Game.Cards;

import Game.Card;
import Game.Card.Color;
import Game.Card.Value;
import Server.ClientSession;

public class WildDrawFour extends Card{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WildDrawFour(Color col, Value val) {
		super(col, val);
	}
	
	@Override
	public void action(ClientSession sess) {
		sess.drawCards(4);
	}
	
}
