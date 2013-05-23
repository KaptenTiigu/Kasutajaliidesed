package Server;

import Game.Card;
import Game.Deck;
import Game.Pile;

public class UnoGame {
	private Deck deck;
	private Pile pile;
	private Card.Color color;
	
	public UnoGame() {
		deck = new Deck();
		pile = new Pile();
	}
	
}
