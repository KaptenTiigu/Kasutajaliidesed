package Server;

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

import Game.Card;
import Game.Pile;
import Game.Player;
import Message.Message;
import Message.WelcomeMessage;
import Message.pickUpCardsMessage;
import Message.serverCardMessage;
import Message.startingPlayersMessage;


public class ClientSession extends Thread {
	private Socket socket;
	private OutboundMessages outQueue;
	private ActiveSessions activeSessions;
	private ObjectInputStream netIn;
	private ObjectOutputStream netOut;
	Message outgoingMessage;
	Message incomingMessage;
	Object incomingObject;
	private UnoGame game;
	private Player player = new Player(getName());
	public ClientSession(Socket s, OutboundMessages out, ActiveSessions as, int n, UnoGame game) throws IOException {
		setName("Player " + n);
		socket = s;
		outQueue = out;
		activeSessions = as;
		this.game = game;
		netOut = new ObjectOutputStream(
				socket.getOutputStream());
		netOut.flush();
		netIn = new ObjectInputStream(
					socket.getInputStream());
		System.out.println( "ClientSession " + this + " stardib..." );	
		game.addPlayer(player);
		
		start();
	}

	public void run() {
		try {
			System.out.println("minu nimi on:" + getName());
			activeSessions.addSession(this);
			//Sisenimise sõnumi saatmine
			sendMessage(new WelcomeMessage(getName()));
			//Mängualguse sõnumi saatmine
			startGame();
			while (true) { 						// Kliendisessiooni elutsükli põhiosa ***
				try {
					incomingObject = netIn.readObject();
					incomingMessage = (Message) incomingObject;
					incomingMessage.onReceive(this);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		// blocked...
		
				
			} 									// **************************************
			
			///KLIENT LAHKUB SIIS SIIN MIDAGI
			
			//outgoingMessage = new TextMessage(getName() + " lahkus...");									
			//outQueue.addMessage(outgoingMessage);
			
		} catch (IOException e) {
			System.out.println("CATCH" + getName());
			//outgoingMessage = new TextMessage(getName() + " - avarii...");		
			//outQueue.addMessage(outgoingMessage);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
	public void addMessage(Message msg) {
		outQueue.addMessage(msg);
	}
	public void sendMessage(Message msg) {
		try {
			if (!socket.isClosed()) {
				System.out.println("Jou, just kirjutasin" + msg);
				netOut.writeObject(msg);
				netOut.reset();
				//outQueue.addMessage(msg);
			} else {
				throw new IOException(); 			// tegelikult: CALL catch()
			}
		} catch (IOException eee) {
			System.out.println("AVARII");
			//outQueue.addMessage(new TextMessage(getName() + " - avarii..."));
			try {
				socket.close();
			} catch (IOException ee) {}
		}
	}
	
	public Message makeMessage(Card card, Card.Color color) {
		if (validate(card)) {
			Message send;
			if (color != null) {
				String next = activeSessions.getNextClientSession(this).getName();
				send = new serverCardMessage(next, card);
			} else {
				
				String next = activeSessions.getNextClientSession(this).getName();
				send = new serverCardMessage(next, card, color);
			}
			return send;
		}
		return null; //???????????????
	}
	/**
	 * Mängu alguses saadetakse mängijatele 5 kaarti.
	 */
	public void startGame() {
		game.startGame();
		System.out.println(player + " lisan kaarte");
		addMessage(new pickUpCardsMessage(pickUpCards(5), this.getName()/*activeSessions.getNextClientSession(this).getName()*/));
	}
	/**
	 * Meetod tagastab desckits võetud kaartide listi
	 * @param amount - võetav kaartide arv
	 * @return kaartide nimekiri
	 */
	public List<Card> pickUpCards(int amount) {
		List<Card> cards = new ArrayList<Card>();
		for (int i=0;i<amount;i++) {
			cards.add(game.giveCard());
		}
		return cards;
		/*if (validate(card)) {
			List<Card> cards = new ArrayList<Card>();
			for (int i=0;i<amount;i++) {
				cards.add(game.giveCard());
			}
			Message send = new pickUpCardsMessage(cards, activeSessions.getNextClientSession(this).getName());
		}*/
}
	
	public boolean validate(Card card) {
		return game.validateCard(player, card);
	}

}