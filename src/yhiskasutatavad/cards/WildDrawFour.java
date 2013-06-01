package yhiskasutatavad.cards;

import server.game.ClientSession;
import yhiskasutatavad.Card;
import yhiskasutatavad.Card.Color;
import yhiskasutatavad.Card.Value;

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
	@Override
	public boolean chooseColor() {
		return true;
	}
	
}
