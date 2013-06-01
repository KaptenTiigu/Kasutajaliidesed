package server.game;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;


import yhiskasutatavad.Card;
import yhiskasutatavad.cards.DrawTwoCard;
import yhiskasutatavad.cards.NumberCard;
import yhiskasutatavad.cards.SkipCard;
import yhiskasutatavad.cards.WildCard;
import yhiskasutatavad.cards.WildDrawFour;



/**
 * Mängus kasutatav kaardipakk.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class Deck extends Pile{

	/**
	 * Pakki segamine.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**
	 * Paki tegemine
	 * @return pakk
	 */
	public Collection<Card> makeDeck() {
		for(Card.Color col : EnumSet.range(Card.Color.BLUE, Card.Color.YELLOW)) {
			for(Card.Value val : EnumSet.range(Card.Value.ZERO, Card.Value.NINE)) {
				cards.add(new NumberCard(col, val));
			}
			cards.add(new WildCard(Card.Color.NONE, Card.Value.WILD));
			cards.add(new WildDrawFour(Card.Color.NONE, Card.Value.WILDDRAWFOUR));
			cards.add(new DrawTwoCard(col, Card.Value.DRAWTWO));
			cards.add(new SkipCard(col, Card.Value.SKIP));
		}
		return cards;
	}

}
