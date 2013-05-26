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
 * Mängu põhiline loogika
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
	 * Mängu alustamine
	 * @param player - mängija
	 */
	public synchronized void startGame(Player player) {
		if (whoseTurn == null) whoseTurn = player.getName();
			if (players.size() != 2) {//CPU LIIGA KÕRGE, HETKEL KAHE PLAYERIGA
				try {
					System.out.println("jään waitima!");
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
				System.out.println("Praegu on " + whoseTurn + " käik");
				
			}			
	}
	
	/**
	 * Enda käigu kontrollimine || waitimisega olid probleemid, praegu ei kasutata
	 * @param player - mängija
	 */
	public void checkMyTurn(Player player) {
		//synchronized(whoseTurn) {
			if(!player.getName().equals(whoseTurn) && outQueue.messagesLength()==0) {
				//System.out.println(player.getName() + ">> pole minu käik, ootan");
				/*synchronized(this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
			}
			//System.out.println(player.getName() + ">> minu käik, käin nüüd");
		//}
	}
	
	/**
	 * Kaardi lisamine Pile (pealmise kaardi tampmine).
	 * @param player - kaardi käija
	 * @param card - käidud kaart
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
				System.out.println("Nüüd on " + whoseTurn + " käik");
				outQueue.addMessage(new ServerCardMessage(whoseTurn, card, card.getColor()));
		}
	}
	
	/**
	 * Järgmise mängija muutmine (See kes peab käima).
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
						//System.out.println("teine player käiaks");
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
	 * Mängija saamine listist
	 * @param player - mängija
	 * @return mängija (listist)
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
	 * Mängulaualt kaartide lisamine decki
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
	 * Mängija lisamine
	 * @param p - mängija
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	/**
	 * Tagastab mängija nime, kelle kord on käia.
	 * @return
	 */
	public String whoseTurn() {
		return whoseTurn;
	}
	/**
	 * Mängija otsimine nime järgi
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
	 * Mängijale kaardi andmine
	 * @param player - mängija
	 * @return kaart
	 */
	public synchronized Card giveCard(Player player) {
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		return a;
	}
	/**
	 * Mängijale kaardi andmine
	 * @param String - mängija nimi
	 * @return kaart
	 */
	public synchronized Card giveCard(String player) {
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		return a;
	}
	/**
	 * MINGI MÕTTETU MEETOD, AINULT TESTIMISEKS
	 * Mängija käes olevate kaartide tagastamine
	 * @param player kaardid.
	 */
	public synchronized void getPlayerCards(Player player) {
		Player b = getPlayer(player);
		System.out.println(b.getCards());
	}
	/**
	 * Käigu valideerimine
	 * @param player - kaardi käija
	 * @param card - tapetav kaart
	 * @return true, kui kõik korras, false kui midagi on valesti
	 */
	public boolean validateCard(Player player, Card card) {
		Player play = getPlayer(player.getName());
		List<Card> hand = play.getCards();
		// Kas kaart on mängijal käes
		for (Card kaart : hand) {
			//System.out.println(kaart.getName());
			if(kaart.getName().equals(card.getName())) {
				System.out.println("SEEES");
				//kas kaarti saab käia pilesse
				try {
					return card.compareCards(getLastPileCard(), null);
				} catch(FirstCardInPileException err) {
					return true;
				}
				
			}
		}
		System.out.println("VALIDEERIMINE EBAÕNNESTUS");
		return false;
	}
	
	/**
	 * Testimiseks, käesolevad kaardid prindib välja
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