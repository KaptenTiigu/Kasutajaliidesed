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
 * Mängu põhiline loogika
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */

public class UnoGame {
	/**
	 * Kaardipakk, kust mängijad võtavad kaarte.
	 */
	private Deck deck;
	/**
	 * Kaardikuhi kuhu mängija käivad kaarte
	 */
	private Pile pile;
	/**
	 * Tapetav värvus (default NONE), valitakse wild kaartide puhul
	 */
	private Card.Color killColor = Card.Color.NONE;
	/**
	 * Mängus osalevad mängijad
	 */
	private List<Player> players = new ArrayList<Player>();
	/**
	 * Sõnumite buhver 
	 */
	private OutboundMessages outQueue;
	/**
	 * Mängija nimi, kelle kord on käia.
	 */
	private String whoseTurn;
	/**
	 * Faili kirjutaja
	 */
	private Writer writer = new Writer();
	/**
	 * Mänguga ühinemise lubamine
	 */
	private boolean joinGame = true;
	
	/**
	 * Mängualguses tehakse valmis pakk, mis segatakse ning samuti ka kaardikuhi(algselt tühi)
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
	 * Mängu alustamine
	 * @param player - mängija
	 */
	public synchronized void startGame(Player player) {
		writer.writeToFile("Mänguga liitus " + player.getName());
		if (whoseTurn == null) whoseTurn = player.getName();
			if (players.size() < 3) {//mängijate arv
				try {
					System.out.println("jään waitima!");
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
				System.out.println("Praegu on " + whoseTurn + " käik");
				writer.writeToFile("3 mängijat koos");
				
			}			
	}
	/**
	 * Kontrollimine kas mänguga saab ühineda.
	 * @return true kui tohib / false kui ei tohi
	 */
	public synchronized boolean joinExistingGame(){
		return joinGame;
	}
	/**
	 * Kaardi lisamine Pile (pealmise kaardi tampmine).
	 * @param player - kaardi käija
	 * @param card - käidud kaart
	 * @throws GameWinException 
	 */
	public void addCardToPile(Player player, Card card, Card.Color color) throws GameWinException {
		if (validateCard(player, card)) {
				killColor = color;
				Card sCard = getCard(card, getPlayer(player.getName()));
				System.out.println("Maha pandav kaart: "+sCard);
				getPlayer(player.getName()).playCard(sCard);
				writer.writeToFile(player.getName() + " käis kaardi "+ card.getName());
				if (getPlayer(player).getCards().isEmpty()) {
					writer.writeToFile("Mäng läbi! Võitis " + player.getName());
					writer.closeWriter();
					throw new GameWinException();
				}
				pile.addCard(card);
				//testimise meetod
				testiKaesOlevaidKaarte(getPlayer(player.getName()));
				
				changeNextPlayer();
				System.out.println("Nüüd on " + whoseTurn + " käik, killColor on" + killColor);
				writer.writeToFile(whoseTurn + "kord on käia");
				outQueue.addMessage(new ServerCardMessage(whoseTurn, card, killColor, players));
		}
	}
	
	/**
	 * Järgmise mängija muutmine (See kes peab käima).
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
	public Card getCard(Card card, Player player) {
			List<Card> hand = player.getCards();
			for (Card kaart : hand) {
				if(kaart.getName().equals(card.getName())) return kaart;
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
	 * Kui mängija lahkub mängust
	 * @param playerName
	 */
	public void leavePlayer(String playerName) {
		//kui tema kord oli käia, siis vahetame käimisjärjekorda
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
		  if (deck.getCards().isEmpty()) {
			   addPileToDeck();
			  }
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		System.out.println(player.getName() + " korjas ülesse " + a.getName());
		writer.writeToFile(player.getName() + " võttis ülesse " + a.getName());
		return a;
	}
	/**
	 * Mängijale kaardi andmine
	 * @param String - mängija nimi
	 * @return kaart
	 */
	public synchronized Card giveCard(String player) {
		  if (deck.getCards().isEmpty()) {
			   addPileToDeck();
			  }
		Card a = deck.getCard();
		Player b = getPlayer(player);
		b.pickupCard(a);
		System.out.println(player + " korjas ülesse " + a.getName());
		writer.writeToFile(player + " võttis ülesse " + a.getName());
		return a;
	}
	/**
	 * Tapetava värvi määramine
	 * @param killColor värv
	 */
	public void setKillColor(Card.Color killColor) {
		this.killColor = killColor;
	}
	/**
	 * Tapetava värvi vaatamine
	 * @return värv
	 */
	public Card.Color getKillColor() {
		return killColor;
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
		for (Card kaart : hand) {
			if(kaart.getName().equals(card.getName())) {
				//System.out.println("SEEES");
				try {
					return card.compareCards(getLastPileCard(), killColor);
				} catch(FirstCardInPileException err) {
					System.out.println("Käidi pile esminen kaart");
					return true;
				}
				
			}
		}
		System.out.println("VALIDEERIMINE EBAÕNNESTUS");
		return false;
	}
	
	/**
	 * Faili kirjutaja tagastaja. Kasutatakse
	 * Server klassis serveri töö lõpetamisel.
	 * Kui server sulgetakse, siis sulgetakse ka kirjutaja.
	 * @return writer objekt, mis tegeleb faili kirjutamisega
	 */
	public Writer getWriter() {
		return writer;
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
	
	/**
	 * Mängust osavõtvate mängijate vaatamine
	 * @return mängijate list
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Uue mängu alustamiseks kustutame mängijad, decki ja pile.
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