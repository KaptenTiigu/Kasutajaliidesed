package server.game;

import java.io.IOException;
import java.util.Iterator;


import yhiskasutatavad.Message;
import yhiskasutatavad.Player;


/**
 * Sõnumite levitaja.
 * Loeb sõnumeid ja saadab need aadresside kaupa ClientSessionitele laiali.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class Broadcaster extends Thread {
	/**
	 * ClientSessionite list
	 */
	private ActiveSessions activeSessions;
	/**
	 * Sõnumite buffer
	 */
	private OutboundMessages outQueue;

	/**
	 * Broadcasteri loomisel antakse ette ActiveSessions, sõnumite buhver ning
	 * pannakse lõim käima.
	 * @param aa ActiveSessions
	 * @param o sõnumite buffer
	 */
	public Broadcaster(ActiveSessions aa, OutboundMessages o) {
		activeSessions = aa;
		outQueue = o;
		start();
	}
	public void run() {
		while (true) {  // Levitaja on igavene...
			Message message = outQueue.getMessage(); 				// blocked
			synchronized (activeSessions) { 				// ActiveSessions lukku!
				Iterator<ClientSession> active = activeSessions.iterator();			
				while (active.hasNext()) {
					ClientSession cli = active.next();
					String adressaat = message.getAdress();
						if (!cli.isAlive()) {//Eemaldame, kui pole enam elus
							active.remove(); 		// ;-)
						} else if (adressaat != null && !adressaat.isEmpty()) {
							if(cli.getName().equals(adressaat)) {
								cli.sendMessage(message);
								break;
							}
						} else {
								cli.sendMessage(message);
							}
				}
			}
		}
		
	}
}