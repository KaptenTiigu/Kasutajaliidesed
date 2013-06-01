package server.game;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yhiskasutatavad.Card;

/**
 * Kaardihunnik, kuhu käiakse mängijate poolt kaarte, mida teised
 * mängijad üritavad siis "tappa"
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class Pile {
	/**
	 * Kaartide list.
	 */
	protected List<Card> cards = new ArrayList<Card>();
	
	/**
	 * Kaardi lisamine
	 * @param card lisatav kaart
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	/**
	 * Kaardi eemaldamine
	 * @param card eemaldatav kaart
	 */
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	/**
	 * Esimese kaardi eemaldamine
	 * @return esimene kaart
	 */
	public Card getCard() {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}
	/**
	 * Viimase (pealmise) kaardi teada saamine
	 * @return viimane (pealmine) kaart
	 */
	public Card watchCard() {
		if (cards.isEmpty()) return null;
		else return cards.get(cards.size() - 1);
	}
	/**
	 * Iteraatori tagastamine
	 * @return iteraator
	 */
	public Iterator<Card> getCardsIterator() {
		return cards.iterator();
	}
	
	/**
	 * Kogu kaartide listi tagastamine
	 * @return kaardid
	 */
	public List<Card> getCards() {
		return cards;
	}

}