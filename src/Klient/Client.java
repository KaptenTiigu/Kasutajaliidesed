package Klient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import Message.Client.ClientCardMessage;
import Message.Client.ClientPickUpMessage;
import Message.Server.WelcomeMessage;

import Game.Card;
import Game.Player;
import Game.Cards.NumberCard;
import Message.Message;


public class Client {
	private Player player;

	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 8888;
		Socket socket;
		String serveriName = "localhost";
		ObjectInputStream netIn;
		ObjectOutputStream netOut;
		Object fromServer;
		Message message;
		LinkedList<Message> inQueue = new LinkedList<Message>();  // FIFO
		InetAddress servAddr = InetAddress.getByName(serveriName);
		Player player = null;
		

		try {
			socket = new Socket(servAddr, port); 				// Server.PORT
		} catch (IOException e) {
			System.out.println("Sry - server pole n�htav :(((");
			return;
		}
		try {
			//System.out.println("socket = " + socket);
			
			// Sokli sisend-v�ljund:
			netIn = new ObjectInputStream(
					socket.getInputStream());
			netOut = new ObjectOutputStream(
					socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			// Saabunud s�numite kuulaja:
			SocketListener l = new SocketListener(socket, netIn, inQueue);
			l.start();
			/*
			 * ESIMENE TS�KKEL, OOTAB SERVRIST ENDALE NIME
			 */
			while(player==null) {
				if (!l.newMessages().isEmpty()) {
					synchronized (l.newMessages()) { 					// lukku !!!
						Iterator<Message> incoming = l.newMessages().iterator();
						while (incoming.hasNext()) {
							player = new Player(incoming.next().getAdress());
							//System.out.println("H�kker");
							incoming.remove();
						}
					}
				}
			}
			/*
			 *TESTIMINE
			 */
			System.out.println("OLEN M�NGIJA : "+ player.getName());
			/*Card c = new NumberCard(Card.Color.BLUE, Card.Value.SIX);
			player.pickupCard(c);
			player.playCard(c);
			Message sonum = new clientCardMessage(c);
			netOut.writeObject(sonum);*/
			/*
			 * P�HILINE ELUTS�KKEL
			 */
				do { 
					inQueue = l.newMessages();
					if (!inQueue.isEmpty()) { 	// kas on midagi saabunud?
						//System.out.println("pole tyhi");
						synchronized (inQueue) { 					// lukku !!!
							Iterator<Message> incoming = inQueue.iterator();
							while (incoming.hasNext()) {
								incoming.next().onReceive(player);
								incoming.remove();
							}
						}
					}
				if(player.getPermission() && !player.getCards().isEmpty()) {
					//Console console = System.console();
					System.out.println("Sisesta kaardi nimi v�i (PICKUP et saada uus kaart," +
							" CARDS et n�he k�es olevaid kaarte)");
					String input = reader.readLine().toUpperCase();
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
							if(card.compareCards(kill, null)) {
								player.playCard(card);
								netOut.writeObject(new ClientCardMessage(card, card.getColor()));
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
					/*
					Card play = player.playCard(player.getCards().get(0));
					System.out.println("k�in kaardi" + play.getColor()+play.getValue());
					netOut.writeObject(new clientCardMessage(play, play.getColor()));
					player.setPermission(false);*/
				}
				} while (true);

		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}
	/*public void makePlayer(Player player) {
		this.player=player;
	}
	
	public void onReceive(Message message) {
		message.onReceive(this);
	}*/

}