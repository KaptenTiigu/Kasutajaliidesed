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
		/*System.out.println("WTF????");
		for(Card.Value val : EnumSet.range(Card.Value.ZERO, Card.Value.NINE)) {
			//cards.add(new NumberCard(Card.Color.RED, val));
			cards.add(new NumberCard(Card.Color.YELLOW, val));
			//cards.add(new DrawTwoCard(Card.Color.RED, Card.Value.DRAWTWO));
		}
		cards.add(new DrawTwoCard(Card.Color.YELLOW, Card.Value.DRAWTWO));
		/*
		cards.add(new DrawTwoCard(Card.Color.RED, Card.Value.DRAWTWO));
		cards.add(new DrawTwoCard(Card.Color.YELLOW, Card.Value.DRAWTWO));*/
		for(Card.Color col : EnumSet.range(Card.Color.BLUE, Card.Color.YELLOW)) {
			for(Card.Value val : EnumSet.range(Card.Value.ZERO, Card.Value.NINE)) {
				cards.add(new NumberCard(col, val));
				//System.out.println(cards.get(cards.size()-1).getValue() + " " + cards.get(cards.size()-1).getColor());
			}
			cards.add(new WildCard(Card.Color.NONE, Card.Value.WILD));
			cards.add(new WildDrawFour(Card.Color.NONE, Card.Value.WILDDRAWFOUR));
			//cards.add(new DrawOneCard(col, Card.Value.DRAWONE));
			cards.add(new DrawTwoCard(col, Card.Value.DRAWTWO));
			cards.add(new SkipCard(col, Card.Value.SKIP));
			//cards.add(new WildCard(col, Card.Value.WILD));
			//cards.add(new WildDrawFour(col, Card.Value.WILDDRAWFOUR));
		}
		return cards;
	}

}
