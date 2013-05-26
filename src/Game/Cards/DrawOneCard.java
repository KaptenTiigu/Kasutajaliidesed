package Game.Cards;

import Game.Card;
import Game.Card.Color;
import Game.Card.Value;
import Server.ClientSession;

/**
 * Kaart, mis annab käiast järgmisele mängijale ühe kaardi juurde ja jätab ta vahele.
 * @author LehoRaiguma
 *
 */
public class DrawOneCard extends Card {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kaardile värvus ja väärtuse andmine.
	 * @param color - kaardi värv
	 * @param value - kaardi väärtus
	 */
	public DrawOneCard(Color color, Value value) {
		super(color, value);
	}
	
	@Override
	public void action(ClientSession sess) {
		sess.drawCards(1);
		//sess.skipNext();
	}
}
