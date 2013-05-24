package Server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Game.Card;
import Game.Deck;
import Game.Pile;
import Game.Player;
import Game.Card.Color;
import Message.startingPlayersMessage;

public class UnoGame {
	private Deck deck;
	private Pile pile;
	private Card.Color color;
	private List<Player> players = new ArrayList<Player>();
	private OutboundMessages outQueue;
	
	public UnoGame(OutboundMessages outQueue) {
		this.outQueue = outQueue;
		deck = new Deck();
		deck.makeDeck();
		deck.shuffle();
		pile = new Pile();
	}
	
	public synchronized List<Player> startGame() {
			if (players.size() != 2) {//CPU LIIGA K�RGE, HETKEL KAHE PLAYERIGA
				try {
					System.out.println("j��n waitima!");
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				outQueue.addMessage(new startingPlayersMessage(players));
				this.notifyAll();
			}			
			System.out.println("Returnin n��d");
			return players;
	}
	
	public void addPileToDeck() {
		List<Card> pileCards = pile.getCards();
		Card last = pileCards.get(pileCards.size()-1);
		Iterator<Card> it = pileCards.iterator();
		while(it.hasNext()){
			Card c = it.next();
			if (c == last) {
				break;
			}
			deck.addCard(c);
			it.remove();
		}
		deck.shuffle();
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	/**
	 * M�ngija otsimine nime j�rgi
	 * @param name
	 * @return
	 */
	public Player getPlayer(String name) {
		for(Player p : players) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Laualt viimase (pealmise) kaardi saamine
	 * @return kaart
	 */
	public Card getLastPileCard() {
		List<Card> pileCards = pile.getCards();
		Card last = pileCards.get(pileCards.size()-1);
		return last;
	}
	
	public synchronized Card giveCard() {
		return deck.getCard();
	}
	
	/**
	 * K�igu valideerimine
	 * @param player - kaardi k�ija
	 * @param card - k�idud kaart
	 * @return true, kui k�ik korras, false kui midagi on valesti
	 */
	public boolean validateCard(Player player, Card card) {
		Player play = getPlayer(player.getName());
		List<Card> hand = play.getCards();
		// Kas kaart on m�ngijal k�es
		for (Card kaart : hand) {
			if(kaart == hand) {
				//kas kaarti saab k�ia pilesse
				return kaart.compareCards(card, card.getColor());
			}
		}
		return true;
	}
	
}