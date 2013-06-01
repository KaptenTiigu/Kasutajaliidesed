package server.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.exceptions.FirstCardInPileException;
import server.exceptions.GameWinException;
import server.messages.ServerCardMessage;
import server.messages.StartingPlayersMessage;
import server.writer.Writer;
import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;
import yhiskasutatavad.Card.Color;

/**
 * M�ngu p�hiline loogika
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */

public class UnoGame {
	/**
	 * Kaardipakk, kust m�ngijad v�tavad kaarte.
	 */
	private Deck deck;
	/**
	 * Kaardikuhi kuhu m�ngija k�ivad kaarte
	 */
	private Pile pile;
	/**
	 * Tapetav v�rvus (default NONE), valitakse wild kaartide puhul
	 */
	private Card.Color killColor = Card.Color.NONE;
	/**
	 * M�ngus osalevad m�ngijad
	 */
	private List<Player> players = new ArrayList<Player>();
	/**
	 * S�numite buhver 
	 */
	private OutboundMessages outQueue;
	/**
	 * M�ngija nimi, kelle kord on k�ia.
	 */
	private String whoseTurn;
	/**
	 * Faili kirjutaja
	 */
	private Writer writer = new Writer();
	/**
	 * M�nguga �hinemise lubamine
	 */
	private boolean joinGame = true;
	
	/**
	 * M�ngualguses tehakse valmis pakk, mis segatakse ning samuti ka kaardikuhi(algselt t�hi)
	 * @param outQueue
	 */
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
		writer.writeToFile("M�nguga liitus " + player.getName());
		if (whoseTurn == null) whoseTurn = player.getName();
			if (players.size() < 3) {//m�ngijate arv
				try {
					System.out.println("j��n waitima!");
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				joinGame = false;
				List<String> playerNames = new ArrayList<String>();
				for (Player a : players) {
					playerNames.add(a.getName());
				}
				this.notifyAll();
				outQueue.addMessage(new StartingPlayersMessage(playerNames, whoseTurn));
				System.out.println("Praegu on " + whoseTurn + " k�ik");
				writer.writeToFile("3 m�ngijat koos");
				
			}			
	}
	/**
	 * Kontrollimine kas m�nguga saab �hineda.
	 * @return true kui tohib / false kui ei tohi
	 */
	public synchronized boolean joinExistingGame(){
		return joinGame;
	}
	/**
	 * Kaardi lisamine Pile (pealmise kaardi tampmine).
	 * @param player - kaardi k�ija
	 * @param card - k�idud kaart
	 * @throws GameWinException 
	 */
	public void addCardToPile(Player player, Card card, Card.Color color) throws GameWinException {
		if (validateCard(player, card)) {
				killColor = color;
				Card sCard = getCard(card, getPlayer(player.getName()));
				System.out.println("Maha pandav kaart: "+sCard);
				getPlayer(player.getName()).playCard(sCard);
				writer.writeToFile(player.getName() + " k�is kaardi "+ card.getName());
				if (getPlayer(player).getCards().isEmpty()) {
					writer.writeToFile("M�ng l�bi! V�itis " + player.getName());
					writer.closeWriter();
					throw new GameWinException();
				}
				pile.addCard(card);
				//testimise meetod
				testiKaesOlevaidKaarte(getPlayer(player.getName()));
				
				changeNextPlayer();
				System.out.println("N��d on " + whoseTurn + " k�ik, killColor on" + killColor);
				writer.writeToFile(whoseTurn + "kord on k�ia");
				outQueue.addMessage(new ServerCardMessage(whoseTurn, card, killColor, players));
		}
	}
	
	/**
	 * J�rgmise m�ngija muutmine (See kes peab k�ima).
	 */
	public void changeNextPlayer() {
		  int currentIndex = 0;
		  synchronized(whoseTurn) {
		   if(whoseTurn.equals(players.get(players.size() -1).getName())) {
		    whoseTurn = players.get(0).getName();
		   } else {
		    for (Player player : players) {
		     if (whoseTurn.equals(player.getName())) {
		      currentIndex = players.indexOf(player);
		      break;
		     } 
		    }
		    whoseTurn = players.get(currentIndex+1).getName();
		   }
		  }
		 }

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
	public Card getCard(Card card, Player player) {
			List<Card> hand = player.getCards();
			for (Card kaart : hand) {
				if(kaart.getName().equals(card.getName())) return kaart;
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
	 * Kui m�ngija lahkub m�ngust
	 * @param playerName
	 */
	public void leavePlayer(String playerName) {
		//kui tema kord oli k�ia, siis vahetame k�imisj�rjekorda
		if(whoseTurn.equals(playerName))changeNextPlayer();
		Iterator<Player> it = players.iterator();
		while(it.hasNext()){
			Player player = it.next();
			if (player.getName().equals(playerName)) {
				players.remove(player);
				break;
			}	
		}
			outQueue.addMessage(new ServerCardMessage(whoseTurn, pile.watchCard(), killColor, players));
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
		  if (deck.getCards().isEmpty()) {
			   addPileToDeck();
			  }
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		System.out.println(player.getName() + " korjas �lesse " + a.getName());
		writer.writeToFile(player.getName() + " v�ttis �lesse " + a.getName());
		return a;
	}
	/**
	 * M�ngijale kaardi andmine
	 * @param String - m�ngija nimi
	 * @return kaart
	 */
	public synchronized Card giveCard(String player) {
		  if (deck.getCards().isEmpty()) {
			   addPileToDeck();
			  }
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		System.out.println(player + " korjas �lesse " + a.getName());
		writer.writeToFile(player + " v�ttis �lesse " + a.getName());
		return a;
	}
	/**
	 * Tapetava v�rvi m��ramine
	 * @param killColor v�rv
	 */
	public void setKillColor(Card.Color killColor) {
		this.killColor = killColor;
	}
	/**
	 * Tapetava v�rvi vaatamine
	 * @return v�rv
	 */
	public Card.Color getKillColor() {
		return killColor;
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
		for (Card kaart : hand) {
			if(kaart.getName().equals(card.getName())) {
				//System.out.println("SEEES");
				try {
					return card.compareCards(getLastPileCard(), killColor);
				} catch(FirstCardInPileException err) {
					System.out.println("K�idi pile esminen kaart");
					return true;
				}
				
			}
		}
		System.out.println("VALIDEERIMINE EBA�NNESTUS");
		return false;
	}
	
	/**
	 * Faili kirjutaja tagastaja. Kasutatakse
	 * Server klassis serveri t�� l�petamisel.
	 * Kui server sulgetakse, siis sulgetakse ka kirjutaja.
	 * @return writer objekt, mis tegeleb faili kirjutamisega
	 */
	public Writer getWriter() {
		return writer;
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
	
	/**
	 * M�ngust osav�tvate m�ngijate vaatamine
	 * @return m�ngijate list
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Uue m�ngu alustamiseks kustutame m�ngijad, decki ja pile.
	 */
	public void clearGame() {
		for (Player player : players) {
			player.getCards().clear();
		}
		players.clear();
		deck.getCards().clear();
		pile.getCards().clear();
	}
	
}