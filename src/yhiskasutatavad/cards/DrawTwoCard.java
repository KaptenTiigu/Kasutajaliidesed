package yhiskasutatavad.cards;

import server.game.ClientSession;
import yhiskasutatavad.Card;
import yhiskasutatavad.Card.Color;
import yhiskasutatavad.Card.Value;

public class DrawTwoCard extends Card {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DrawTwoCard(Color color, Value value) {
		super(color, value);
	}
	
	@Override
	public void action(ClientSession sess) {
		sess.drawCards(2);
	}

	@Override
	public boolean chooseColor() {
		// TODO Auto-generated method stub
		return false;
	}
}
