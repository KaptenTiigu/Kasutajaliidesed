package yhiskasutatavad.cards;

import server.game.ClientSession;
import yhiskasutatavad.Card;
import yhiskasutatavad.Card.Color;
import yhiskasutatavad.Card.Value;

public class SkipCard extends Card{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SkipCard(Color color, Value value) {
		super(color, value);
	}
	
	@Override
	public void action(ClientSession sess) {
		sess.skipTurn();
		
	}

	@Override
	public boolean chooseColor() {
		return false;
	}

}