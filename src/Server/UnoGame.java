package Server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exceptions.FirstCardInPileException;

import Message.Message;
import Message.Server.ServerCardMessage;
import Message.Server.StartingPlayersMessage;


import Game.Card;
import Game.Deck;
import Game.Pile;
import Game.Player;
import Game.Card.Color;

/**
 * M�ngu p�hiline loogika
 * @author LehoRaiguma
 *
 */
public class UnoGame {
	private Deck deck;
	private Pile pile;
	private Card.Color color;
	private List<Player> players = new ArrayList<Player>();
	private OutboundMessages outQueue;
	private String whoseTurn;
	
	public UnoGame(OutboundMessages outQueue) {
		this.outQueue = outQueue;
		deck = new Deck();
		deck.makeDeck();
		deck.shuffle();
		pile = new Pile();
	}
	
	/**
	 * M�ngu alustamine
	 * @param player - m�ngija
	 */
	public synchronized void startGame(Player player) {
		if (whoseTurn == null) whoseTurn = player.getName();
			if (players.size() != 2) {//CPU LIIGA K�RGE, HETKEL KAHE PLAYERIGA
				try {
					System.out.println("j��n waitima!");
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				List<String> playerNames = new ArrayList<String>();
				for (Player a : players) {
					playerNames.add(a.getName());
				}
				this.notifyAll();
				outQueue.addMessage(new StartingPlayersMessage(playerNames, whoseTurn));
				System.out.println("Praegu on " + whoseTurn + " k�ik");
				
			}			
	}
	
	/**
	 * Enda k�igu kontrollimine || waitimisega olid probleemid, praegu ei kasutata
	 * @param player - m�ngija
	 */
	public void checkMyTurn(Player player) {
		//synchronized(whoseTurn) {
			if(!player.getName().equals(whoseTurn) && outQueue.messagesLength()==0) {
				//System.out.println(player.getName() + ">> pole minu k�ik, ootan");
				/*synchronized(this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
			}
			//System.out.println(player.getName() + ">> minu k�ik, k�in n��d");
		//}
	}
	
	/**
	 * Kaardi lisamine Pile (pealmise kaardi tampmine).
	 * @param player - kaardi k�ija
	 * @param card - k�idud kaart
	 */
	public void addCardToPile(Player player, Card card) {
		if (validateCard(player, card)) {
				Card sCard = getCard(card);
				getPlayer(player.getName()).playCard(sCard/*getCard(card)*/);
				pile.addCard(card);
				/*synchronized(this) {
					this.notifyAll();
				}*/
				//testimise meetod
				testiKaesOlevaidKaarte(getPlayer(player.getName()));
				
				changeNextPlayer();
				System.out.println("N��d on " + whoseTurn + " k�ik");
				outQueue.addMessage(new ServerCardMessage(whoseTurn, card, card.getColor()));
		}
	}
	
	/**
	 * J�rgmise m�ngija muutmine (See kes peab k�ima).
	 */
	public void changeNextPlayer() {
		boolean next = false;
		synchronized(whoseTurn) {
			if(whoseTurn.equals(players.get(players.size() -1).getName())) {
				whoseTurn = players.get(0).getName();
			} else {
				for (Player player : players) {
					if (whoseTurn.equals(player.getName())) {
						next = true;
					} else if (next) {
						whoseTurn = player.getName();
						//System.out.println("teine player k�iaks");
					}			
				}
			}
		}
	}
	/*public void addCardToPlayer(Player player, Card card) {
		for (Player a : players) {
			if(a == player) {
				a.pickupCard(card);
			}
		}
	}*/
	/**
	 * M�ngija saamine listist
	 * @param player - m�ngija
	 * @return m�ngija (listist)
	 */
	public Player getPlayer(Player player) {
		for (Player a : players) {
			if(a.getName().equals(player.getName())) {
				return a;
			}
		}
		return null;
	}
	/**
	 * Tagastab serveri poolse kaardi objekti
	 * @param card - kliendi poolne kaart
	 * @return serveri poolne kaart
	 */
	public Card getCard(Card card) {
		for(Player player : players) {
			//Player play = getPlayer(player.getName());
			List<Card> hand = player.getCards();
			for (Card kaart : hand) {
				if(kaart.getName().equals(card.getName())) return kaart;
				//System.out.println(kaart.getName());
			}
		}
		return null;
	}
	/**
	 * M�ngulaualt kaartide lisamine decki
	 */
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
	
	/**
	 * M�ngija lisamine
	 * @param p - m�ngija
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	/**
	 * Tagastab m�ngija nime, kelle kord on k�ia.
	 * @return
	 */
	public String whoseTurn() {
		return whoseTurn;
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
	 * @throws FirstCardInPileException - kui Piles puuduvad kaardid
	 */
	public Card getLastPileCard() throws FirstCardInPileException {
		List<Card> pileCards = pile.getCards();
		if (pileCards.isEmpty()) throw new FirstCardInPileException();
		Card last = pileCards.get(pileCards.size()-1);
		return last;
	}
	
	/**
	 * M�ngijale kaardi andmine
	 * @param player - m�ngija
	 * @return kaart
	 */
	public synchronized Card giveCard(Player player) {
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		return a;
	}
	/**
	 * M�ngijale kaardi andmine
	 * @param String - m�ngija nimi
	 * @return kaart
	 */
	public synchronized Card giveCard(String player) {
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		return a;
	}
	/**
	 * MINGI M�TTETU MEETOD, AINULT TESTIMISEKS
	 * M�ngija k�es olevate kaartide tagastamine
	 * @param player kaardid.
	 */
	public synchronized void getPlayerCards(Player player) {
		Player b = getPlayer(player);
		System.out.println(b.getCards());
	}
	/**
	 * K�igu valideerimine
	 * @param player - kaardi k�ija
	 * @param card - tapetav kaart
	 * @return true, kui k�ik korras, false kui midagi on valesti
	 */
	public boolean validateCard(Player player, Card card) {
		Player play = getPlayer(player.getName());
		List<Card> hand = play.getCards();
		// Kas kaart on m�ngijal k�es
		for (Card kaart : hand) {
			//System.out.println(kaart.getName());
			if(kaart.getName().equals(card.getName())) {
				System.out.println("SEEES");
				//kas kaarti saab k�ia pilesse
				try {
					return card.compareCards(getLastPileCard(), null);
				} catch(FirstCardInPileException err) {
					return true;
				}
				
			}
		}
		System.out.println("VALIDEERIMINE EBA�NNESTUS");
		return false;
	}
	
	/**
	 * Testimiseks, k�esolevad kaardid prindib v�lja
	 * @param player
	 */
	public void testiKaesOlevaidKaarte(Player player) {
		Player play = getPlayer(player.getName());
		List<Card> hand = play.getCards();
		for (Card kaart : hand) {
			System.out.println(kaart.getName());
		}
	}
	
}