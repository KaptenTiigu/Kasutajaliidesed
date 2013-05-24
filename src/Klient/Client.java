package Klient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import Game.Card;
import Game.NumberCard;
import Game.Player;
import Message.Message;
import Message.WelcomeMessage;
import Message.clientCardMessage;


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
			System.out.println("Sry - server pole nähtav :(((");
			return;
		}
		try {
			System.out.println("socket = " + socket);
			
			// Sokli sisend-väljund:
			netIn = new ObjectInputStream(
					socket.getInputStream());
			netOut = new ObjectOutputStream(
					socket.getOutputStream());

			// Saabunud sõnumite kuulaja:
			SocketListener l = new SocketListener(socket, netIn, inQueue);
			l.start();
			/*
			 * ESIMENE TSÜKKEL, OOTAB SERVRIST ENDALE NIME
			 */
			while(player==null) {
				if (!l.newMessages().isEmpty()) {
					synchronized (l.newMessages()) { 					// lukku !!!
						Iterator<Message> incoming = l.newMessages().iterator();
						while (incoming.hasNext()) {
							player = new Player(incoming.next().getAdress());
							System.out.println("Häkker");
							incoming.remove();
						}
					}
				}
			}
			/*
			 *TESTIMINE
			 */
			System.out.println("OLEN MÄNGIJA : "+ player.getName());
			/*Card c = new NumberCard(Card.Color.BLUE, Card.Value.SIX);
			player.pickupCard(c);
			player.playCard(c);
			Message sonum = new clientCardMessage(c);
			netOut.writeObject(sonum);*/
			/*
			 * PÕHILINE ELUTSÜKKEL
			 */
			do { 
				inQueue = l.newMessages();
				if (!inQueue.isEmpty()) { 	// kas on midagi saabunud?
					System.out.println("pole tyhi");
					synchronized (inQueue) { 					// lukku !!!
						Iterator<Message> incoming = inQueue.iterator();
						while (incoming.hasNext()) {
							incoming.next().onReceive(player);
							incoming.remove();
						}
					}
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