package Game;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import Game.Cards.DrawOneCard;
import Game.Cards.DrawTwoCard;
import Game.Cards.NumberCard;
import Game.Cards.SkipCard;
import Game.Cards.WildCard;
import Game.Cards.WildDrawFour;


public class Deck extends Pile{

	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Collection<Card> makeDeck() {
		for(Card.Value val : EnumSet.range(Card.Value.ZERO, Card.Value.NINE)) {
			cards.add(new NumberCard(Card.Color.RED, val));
		}
		/*for(Card.Color col : Card.Color.values()) {
			for(Card.Value val : EnumSet.range(Card.Value.ZERO, Card.Value.NINE)) {
				cards.add(new NumberCard(col, val));
				//System.out.println(cards.get(cards.size()-1).getValue() + " " + cards.get(cards.size()-1).getColor());
			}
			
			cards.add(new DrawOneCard(col, Card.Value.DRAWONE));
			cards.add(new DrawTwoCard(col, Card.Value.DRAWTWO));
			cards.add(new SkipCard(col, Card.Value.SKIP));
			cards.add(new WildCard(col, Card.Value.WILD));
			cards.add(new WildDrawFour(col, Card.Value.WILDDRAWFOUR));
		}*/
		return cards;
	}

}
