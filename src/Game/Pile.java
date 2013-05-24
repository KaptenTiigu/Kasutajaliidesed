package Game;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pile {
	/**
	 * Kaardihunnik, kuhu käiakse.
	 */
	protected List<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public Card getCard() {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}
	
	public Iterator<Card> getCardsIterator() {
		return cards.iterator();
	}
	
	public List<Card> getCards() {
		return cards;
	}

}