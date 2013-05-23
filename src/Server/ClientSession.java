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
import java.util.Iterator;

import Game.Card;
import Game.Player;
import Message.Message;
import Message.ServerCardMessage;
import Message.TextMessage;
import Message.serverCardMessage;


public class ClientSession extends Thread {
	private Socket socket;
	private OutboundMessages outQueue;
	private ActiveSessions activeSessions;
	private ObjectInputStream netIn;
	private ObjectOutputStream netOut;
	Message outgoingMessage;
	Message incomingMessage;
	Object incomingObject;
	//private OutputStream os;// = s.getOutputStream();  
	//private ObjectOutputStream oos;
	public ClientSession(Socket s, OutboundMessages out, ActiveSessions as, int n) throws IOException {
		setName("Player " + n);
		socket = s;
		outQueue = out;
		activeSessions = as;
		netIn = new ObjectInputStream(
					socket.getInputStream());
		netOut = new ObjectOutputStream(
					socket.getOutputStream());
		System.out.println( "ClientSession " + this + " stardib..." );
		start();
	}

	public void run() {
		try {
			//netOut.println("Teretulemast jutustama!");
			//netOut.println("Palun ytle oma nimi:");
			incomingObject = netIn.readObject(); 	// blocked - ootab kliendi nime
			if(incomingObject != null) {
				incomingMessage = (Message) incomingObject;
				super.setName(incomingMessage.get)
				activeSessions.addSession(this); 	// registreerime end aktiivsete seansside loendis
				outgoingMessage = new TextMessage(name + " tuli sisse...");
				outQueue.addMessage(outgoingMessage); 			// teatame sellest kõigile
			}
			super.setName(name); 				// anname endale nime
			while (true) { 						// Kliendisessiooni elutsükli põhiosa ***
				incomingObject = netIn.readObject();		// blocked...
				incomingMessage = (Message) incomingObject;
				incomingMessage.onReceive(this);
				
				//netIn.
				
				/*if (str.equals("WHO")) {
					Iterator<ClientSession> iterator = activeSessions.iterator();
					String nimed= "";
					while (iterator.hasNext()) {
						ClientSession cli = iterator.next();
						nimed += cli.getName() + " ";
					}
					netOut.println("Serveris on: " + nimed);
					
				} else {
				String outMsg = getName() + " lausub: " + str;
				
				if (str == null) {
					continue; 					// tuli EOF
				}
				if (str.equalsIgnoreCase("END")) {
					break;
				}
				
				outQueue.addMessage(outMsg);
				}*/
				
			} 									// **************************************
			outgoingMessage = new TextMessage(getName() + " lahkus...");									
			outQueue.addMessage(outgoingMessage);
			
		} catch (IOException e) {
			outgoingMessage = new TextMessage(getName() + " - avarii...");		
			outQueue.addMessage(outgoingMessage);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}

	public void sendMessage(Message msg) {
		try {
			if (!socket.isClosed()) {
				/***/
				netOut.writeObject(msg);
			} else {
				throw new IOException(); 			// tegelikult: CALL catch()
			}
		} catch (IOException eee) {
			//outQueue.addMessage(new TextMessage(getName() + " - avarii..."));
			try {
				socket.close();
			} catch (IOException ee) {}
		}
	}
	
	public Message makeMessage(Card card, Card.Color color) {
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

}