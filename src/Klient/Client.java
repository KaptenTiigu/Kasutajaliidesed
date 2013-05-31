package Klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import exceptions.UnknownColorNameException;
import exceptions.UnknownInputException;
import exceptions.UnsuitableCardException;
import gui.uix;

import Message.Message;
import Message.Client.ClientCardMessage;
import Message.Client.ClientPickUpMessage;

import Game.Card;
import Game.Player;


/**
 * Siin klassis toimub kliendi p�hiline tegevus: s�numite koostamine ja vastuv�tmine, 
 * kasutaja sisendi saamine ning m�nguloogika kontrollimine.
 * 
 * @author Ivo Uutma
 * @author Marko Vanaveski
 *
 */
public class Client extends Thread {
	/**
	 * M�ngija objekt
	 */
	private Player player;
	/**
	 * Serveri port
	 */
	private int port = 8888;
	/**
	 * Socket
	 */
	private Socket socket;
	/**
	 * Serveri nimi
	 */
	private String serveriName = "25.57.221.19";
	/**
	 * Objektide sisselugemise striim.
	 */
	private ObjectInputStream netIn;
	/**
	 * Objektide kirjutamise striim
	 */
	private ObjectOutputStream netOut;
	/**
	 * Sissetulnud s�numite list.
	 */
	private LinkedList<Message> inQueue = new LinkedList<Message>();  // FIFO
	
	private uix userInterface;
	
	private InetAddress servAddr;
	/**
	 * Kasutaja konsooli kirjutamise lugeja
	 */
	private BufferedReader reader;
	private boolean stopThread = false;
	private boolean sending = false;

	/**
	 * Konstruktoris pannakse l�im k�ima.
	 */
	public Client() {
		start();
	}
	@Override
	public void run() {
		try {
			servAddr = InetAddress.getByName(serveriName);
			socket = new Socket(servAddr, port); 				// Server.PORT
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Sry - server pole n�htav :(((");
			return;
		}
		try {
			// Sokli sisend-v�ljund:
			netIn = new ObjectInputStream(
					socket.getInputStream());
			netOut = new ObjectOutputStream(
					socket.getOutputStream());
			reader = new BufferedReader(
					new InputStreamReader(System.in));
			// Saabunud s�numite kuulaja:
			SocketListener l = new SocketListener(socket, netIn, inQueue);
			l.start();
			/*
			 * P�HILINE ELUTS�KKEL
			 */
			while(!stopThread) {
				// Uute s�numite dekodeerimine
				inQueue = l.newMessages();
				if (!inQueue.isEmpty()) { 	// kas on midagi saabunud?
					synchronized (inQueue) { 					// lukku !!!
						Iterator<Message> incoming = inQueue.iterator();
						while (incoming.hasNext()) {
							setSending(false);
							incoming.next().onReceive(this);
							//if(player.getKillCard()!=null)userInterface.changekillCard(player.getKillCard().getName());
							incoming.remove();
						}
					}
				}
				//kasutaja sisendi v�tmine
				/*if(player != null) {
					if(player.getPermission() && !player.getCards().isEmpty()) {
						System.out.println("Sisesta kaardi nimi v�i (PICKUP et saada uus kaart," +
								" CARDS et n�he k�es olevaid kaarte)");
						String input = reader.readLine().toUpperCase();
						if (input.equals("PICKUP")) {
							pickUpInput();
						} else if(input.equals("CARDS")) {
							cardsInput();
						} else {
							Card card;
							Card.Color color;
							try {
								card = validateCardInput(input); //teeb sisendist kaardi
								checkCardSuitability(card); //vaatab kas kaart sobib
								color = chooseColor(card); // laseb kasutajal vajadusel v�rvi valida
								player.playCard(card);
								netOut.writeObject(new ClientCardMessage(card, color));
								player.setPermission(false);
							} catch (UnknownInputException e) {
								System.out.println("Kaardi nimi valesti sisestatud!");
							} catch (UnsuitableCardException e) {
								System.out.println("Seda kaarti ei saa praegu k�ia");
							} catch (UnknownColorNameException e) {
								System.out.println("Valesti sisestatud v�rv");
							}
						}
					}//permission
				}*///player!=null
			} //while true
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("M�ng suletakse...");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}//run
	public void makeUserInterFace(){
		userInterface= new uix(this);
	}
	
	public Card.Color getKillColor() {
		return player.getColor();
	}
	public void setPermission(boolean permission) {
		player.setPermission(permission);
	}
	/**
	 * M�ngija tegemine
	 * @param p - m�ngija
	 */
	public void setPlayer(Player p) {
		player = p;
		player.setUserInterFace(userInterface);
	}
	
	public void setSending(boolean sending) {
		this.sending = sending;
	}
	public boolean getSending() {
		return sending;
	}
	public boolean checkPermission() {
		if(player.getPermission())return true;
		else return false;
	}
	
	public void gameWinner(String winner) {
		userInterface.win(winner);
	}
	
	public void changeUIhand() {
		userInterface.changeHand(player.getCards());
	}
	public void addKillCard(String cardName, List<Player> players) {
		List<Player> newList = new ArrayList<Player>();
		for (Player listPlayer : players) {
			if(!listPlayer.getName().equals(player.getName())) {
				newList.add(listPlayer);
			}
		}
		userInterface.changekillCard(cardName, newList);
	}
	/**
	 * Threadi peatamine.
	 */
	public void setStopThread() {
		stopThread = true;
	}
	/**
	 * M�ngija tagastamine
	 * @return m�ngija
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * KASUTAJA INPUT
	 * Pilest uue kaardi �lesv�tmise s�numi tegemine.
	 * @throws IOException
	 */
	public void pickUpInput() throws IOException {
		netOut.writeObject(new ClientPickUpMessage());
		player.setPermission(false);
	}
	
	/**
	 * KASUTAJA INPUT
	 * K�es olevate kaartide n�itamine.
	 */
	public void cardsInput() {
		player.kaartidePrint();
	}
	
	/**
	 * KASUTAJA INPUT
	 * Sisendi teisendamine kaardiks
	 * @param input sisend
	 * @return kaart
	 * @throws UnknownInputException kui sellise sisendiga kaarti ei leidu
	 */
	public Card validateCardInput(String input) throws UnknownInputException {
		Card card = player.getCardByName(input);
		if(card == null) throw new UnknownInputException();
		else return card;
	}
	
	/**
	 * Kaardi k�imis�igsuse kontroll
	 * @param card kaart
	 * @throws UnsuitableCardException kui sellist kaarti ei tohi k�ia
	 */
	public void checkCardSuitability(Card card) throws UnsuitableCardException {
		Card kill = player.getKillCard();
		if(kill != null) {
			if(!card.compareCards(kill, player.getColor())) throw new UnsuitableCardException();
		}
	}
	
	public void sendGIUwildCard(Card card, Card.Color color) {
		try {
			netOut.writeObject(new ClientCardMessage(card, color));
		} catch (IOException e) {}
	}
	/**
	 * KASUTAJA INPUT
	 * V�rvi valimine.
	 * @param card kaart
	 * @return v�rv 
	 * @throws IOException
	 * @throws UnknownColorNameException kui sellise nimega v�rvi ei eksisteeri
	 */
	public Card.Color chooseColor(Card card) throws IOException, UnknownColorNameException {
		Card.Color color = Card.Color.NONE;
		if(card.chooseColor()) {
			while(color==Card.Color.NONE) {
				System.out.println("Sisesta kaardi v�rvus, mille peab j�rgmine m�ngija k�ima");
				String input = reader.readLine().toUpperCase();
				//exceptioni peaks tegelt siia vist panema
				switch(input) {
					case "BLUE":
						return Card.Color.BLUE;
					case "RED":
						return Card.Color.RED;
					case "GREEN":
						return Card.Color.GREEN;
					case "YELLOW":
						return Card.Color.YELLOW;
					default:
						throw new UnknownColorNameException();
				}
			}
		}
		player.addColor(Card.Color.NONE);
		return Card.Color.NONE;
	}
	/*
	 * MINGI M�TTETU JURA, VANA KOOD LIHTSALT J�TSIN ALLES PRAEGU
	 */
	/*public void translateInput(String input) throws IOException {
		if (input.equals("PICKUP")) {
			netOut.writeObject(new ClientPickUpMessage());
			player.setPermission(false);
		} else if(input.equals("CARDS")) {
			player.kaartidePrint();
		} else {
				Card card = player.getCardByName(input);
				if(card != null) {
					System.out.println(input);
					//player.addKillCard(card);
					Card kill = player.getKillCard();
					if(kill != null) {
						if(card.compareCards(kill, player.getColor())) {
							Card.Color color = null;
							//System.out.println("NULL");
							if(card.chooseColor()) {
								//System.out.println("true");
								while(color==null) {
									System.out.println("Sisesta kaardi v�rvus, mille peab j�rgmine m�ngija k�ima");
									input = reader.readLine().toUpperCase();
									switch(input) {
									case "BLUE":
										color = Card.Color.BLUE;
										break;
									case "RED":
										color = Card.Color.RED;
										break;
									case "GREEN":
										color = Card.Color.GREEN;
										break;
									case "YELLOW":
										color = Card.Color.YELLOW;
										break;
									default:
										System.out.println("Valesti sisestatud v�rv!");
									}
								}
							} else {
								player.addColor(null);
							}
							player.playCard(card);
							netOut.writeObject(new ClientCardMessage(card, colorcard.getColor()));
							System.out.println("Saatsin s�numi!");
							player.setPermission(false);
						} else {
							System.out.println("Seda kaarti ei saa praegu k�ia");
						}
					} else {
						System.out.println("Kill kaart puudus");
						player.playCard(card);
						netOut.writeObject(new ClientCardMessage(card, card.getColor()));
						System.out.println("Saatsin s�numi!");
						player.setPermission(false);
					}
					
				} else {
					System.out.println("Kaardi nimi valesti sisestatud!");
				}
				}
	}*/
	public void playCard(Card card) {
		// TODO Auto-generated method stub
		player.playCard(card);
		try {
			netOut.writeObject(new ClientCardMessage(card, Card.Color.NONE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
