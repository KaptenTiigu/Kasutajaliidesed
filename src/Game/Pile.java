package Game;
import java.util.ArrayList;
import java.util.List;

public class Pile {
	protected List<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public Card getCard() {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}

}
