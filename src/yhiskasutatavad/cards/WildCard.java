package yhiskasutatavad.cards;

import server.game.ClientSession;
import yhiskasutatavad.Card;
import yhiskasutatavad.Card.Color;
import yhiskasutatavad.Card.Value;

public class WildCard extends Card{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WildCard(Color col, Value val) {
		super(col, val);
	}
	
	@Override
	public void action(ClientSession sess) {
		
	}

	@Override
	public boolean chooseColor() {
		return true;
	}
	
}