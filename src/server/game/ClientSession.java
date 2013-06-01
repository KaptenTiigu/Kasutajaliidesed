package server.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.Server;
import server.exceptions.FirstCardInPileException;
import server.exceptions.GameWinException;
import server.exceptions.TooManyPlayersException;
import server.messages.GameWinMessage;
import server.messages.PickUpCardsMessage;
import server.messages.RejectMessage;
import server.messages.ServerCardMessage;
import server.messages.StartingPlayersMessage;
import server.messages.WelcomeMessage;
import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;
import yhiskasutatavad.Card.Color;





/**
 * Serveri poolt loodud kliendile loodud lõim, mis saadab ja võtab vastu sõnumeid
 * ning töötleb neid.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class ClientSession extends Thread {
	/**
	 * Socket
	 */
	private Socket socket;
	/**
	 * Välja saadetavate sõnumite buffer 
	 */
	private OutboundMessages outQueue;
	/**
	 * Aktiivsete klientide list (osalevas mängus)
	 */
	private ActiveSessions activeSessions;
	/**
	 * Sisselugemise striim
	 */
	private ObjectInputStream netIn;
	/**
	 * Väljakirjutamise striim
	 */
	private ObjectOutputStream netOut;
	/**
	 * Sisse tulnud sõnum, mis genereeritakse sissetulnud objektist.
	 */
	Message incomingMessage;
	/**
	 * Sisse tulnud objekt
	 */
	Object incomingObject;
	/**
	 * Mängu objekt
	 */
	private UnoGame game;
	/**
	 * Playeri objekt
	 */
	private Player player;
	/**
	 * Maksimum arv mängijaid mängu kohta
	 */
	private final int maxNumberOfPlayers = 3;
	/**
	 * Lõime peatamine
	 */
	private boolean stopThread = false;
	/**
	 * Serveri objekt
	 */
	private Server server;
	public ClientSession(Socket s, OutboundMessages out, ActiveSessions as, int n, UnoGame game, Server server) throws IOException {
		setName("Player " + n);
		player = new Player(getName());
		socket = s;
		outQueue = out;
		activeSessions = as;
		this.server = server;
		this.game = game;
		netOut = new ObjectOutputStream(
				socket.getOutputStream());
		netIn = new ObjectInputStream(
					socket.getInputStream());
		System.out.println( "ClientSession " + this + " stardib..." );	
		// Paneme lõime käima
		start();
	}

	public void run() {
		try {
			   if (activeSessions.getNumberOfClients() == maxNumberOfPlayers) {
				   netOut.writeObject(new RejectMessage(getName()));
				    throw new TooManyPlayersException();
			   }
			System.out.println("minu nimi on:" + getName());
			activeSessions.addSession(this);
			//Sisenimise sõnumi saatmine
			sendMessage(new WelcomeMessage(getName()));
			//Mängualguse sõnumi saatmine
			startGame();
			while (!stopThread) { 						// Kliendisessiooni elutsükli põhiosa ***
				try {
					incomingObject = netIn.readObject();
					incomingMessage = (Message) incomingObject;
					incomingMessage.onReceive(this);
				} catch (ClassNotFoundException e) {}
			}	
		} catch (IOException e) {//Sisse lugemisel error
			System.out.println("Lahkus: " + getName());
			if(!game.getPlayers().isEmpty())game.leavePlayer(player.getName());
		} catch (TooManyPlayersException e) {
			System.out.println("UUS MÄNGIJA, AGA MÄNGIJATE LIIMIT TÄIS" + getName());
		} finally {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
	/**
	 * Sõnumi edastamine buffrisse
	 * @param msg
	 */
	public void addMessage(Message msg) {
		outQueue.addMessage(msg);
			}
	
	/**
	 * Threadi seisma panemine
	 */
	public void setStopThread() {
		stopThread = true;
	}
	/**
	 * Mängija võtab ühe kaardi ülesse
	 */
	public void pickUpOneCard() {
		List<Card> cards = new ArrayList<Card>();
		cards.add(game.giveCard(player));
		game.changeNextPlayer();
		System.out.println(player.getName() +">> muutsin järjekorda nüüd on (" + game.whoseTurn()+") kord.");
		outQueue.addMessage(new PickUpCardsMessage(cards, this.getName()));
		try {
			outQueue.addMessage(new ServerCardMessage(game.whoseTurn(), game.getLastPileCard(), game.getKillColor(), game.getPlayers()));
		} catch (FirstCardInPileException e) {
			//Kui lauale pole veel ühtegi kaarti käidud
			outQueue.addMessage(new ServerCardMessage(game.whoseTurn(), null, game.getKillColor(), game.getPlayers()));
		}
	}
	
	/**
	 * Sõnumi saatmine
	 * @param msg - sõnum
	 */
	public synchronized void sendMessage(Message msg) {
		try {
			if (!socket.isClosed()) {
				netOut.writeObject(msg);
				netOut.reset();
			} else {
				throw new IOException();
			}
		} catch (IOException eee) {
			eee.printStackTrace();
			System.out.println("AVARII" + player.getName());
			try {
				socket.close();
			} catch (IOException ee) {}
		}
	}
	
	/**
	 * Lauale käidavast kaardist sõnumi tegemine
	 * @param card - lauale käidud kaart
	 * @param color - kui wild kaart, siis valitud värv / muidu Card.Color.NONE
	 * @return sõnum
	 */
	public Message makeMessage(Card card, Card.Color color) {
		if (validate(card)) {
			Message send;
			if (color != null) {
				String next = activeSessions.getNextClientSession(this).getName();
				send = new ServerCardMessage(next, card, Card.Color.NONE, game.getPlayers());
			} else {
				
				String next = activeSessions.getNextClientSession(this).getName();
				send = new ServerCardMessage(next, card, color, game.getPlayers());
			}
			return send;
		}
		return null; //???????????????
	}
	/**
	 * Mängu alguses saadetakse mängijatele 5 kaarti.
	 */
	public void startGame() {
		game.addPlayer(player);
		game.startGame(player);
		System.out.println(player.getName() + " annan alustuseks 5 kaarti");
		addMessage(new PickUpCardsMessage(pickUpCards(5), this.getName()/*activeSessions.getNextClientSession(this).getName()*/));
	}
	/**
	 * Meetod tagastab desckits võetud kaartide listi
	 * @param amount - võetav kaartide arv
	 * @return kaartide nimekiri
	 */
	public List<Card> pickUpCards(int amount) {
		List<Card> cards = new ArrayList<Card>();
		for (int i=0;i<amount;i++) {
			cards.add(game.giveCard(player));
		}
		return cards;
	}
	
	/**
	 * Annab järgmisele mängijale kaarte juurde JA JÄTAB VAHELE
	 * @param amount - kaartide kogus mis juurde antakse
	 */
	public void drawCards(int amount) {
		game.changeNextPlayer();
		List<Card> cards = new ArrayList<Card>();
		System.out.println("anna");
		for (int i=0;i<amount;i++) {
			System.out.println("arv");
			cards.add(game.giveCard(game.whoseTurn()));
		}
		addMessage(new PickUpCardsMessage(cards, game.whoseTurn()));
	}
	
	/**
	 * Järgmine mängija jätab käigu vahele.
	 */
	public void skipTurn() {
		game.changeNextPlayer();
	}
	
	/**
	 * Kliendilt tulnud kaardi valideerimine.
	 * @param card - kaart
	 * @return boolean, kas valideerimine õnnestus
	 */
	public boolean validate(Card card) {
		return game.validateCard(player, card);
	}

	/**
	 * Kliendilt saabunud sõnumi töötlemine.
	 * @param kaart
	 * @param varv
	 */
	public void recieveClientCard(Card card, Card.Color varv) {
		//game.validateCard(player, card);
		System.out.println(getName() +" saatis kaardi: " + card.getName()+", värv: "+ varv);
		if (validate(card)) {
			System.out.println("HEI VALIDEERITUD!");
			card.action(this);
			System.out.println("HEI ACTION TEHTUD!");
			try {
				game.addCardToPile(player, card, varv);
			} catch (GameWinException e) {
				outQueue.addMessage(new GameWinMessage(card, getName()));
				game.clearGame();
				//setStopThread();
			}
		}	
	}
	/**
	 * Serverile teadandmine uue mängu soovist.
	 */
	public void newGame() {
		server.newGame(this);
	}

	/**
	 * Uue mängu alustamine.
	 * 
	 * @param outQueue2 väljundsõnumite puhver
	 * @param activeSessions2 aktiivsete klientide nimekiri
	 * @param game2 mängu objekt
	 */
	public void startNewGame(OutboundMessages outQueue2,
		ActiveSessions activeSessions2, UnoGame game2) {
		outQueue = outQueue2;
		activeSessions = activeSessions2;
		this.game = game2;
		activeSessions.addSession(this);
		startGame();
	}

}