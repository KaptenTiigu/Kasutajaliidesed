package Game.Cards;

import Game.Card;
import Game.Card.Color;
import Game.Card.Value;

/**
 * Kaart, mis annab k�iast j�rgmisele m�ngijale �he kaardi juurde ja j�tab ta vahele.
 * @author LehoRaiguma
 *
 */
public class DrawOneCard extends Card {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kaardile v�rvus ja v��rtuse andmine.
	 * @param color - kaardi v�rv
	 * @param value - kaardi v��rtus
	 */
	public DrawOneCard(Color color, Value value) {
		super(color, value);
	}
	
	@Override
	public void action() {
		
	}
}