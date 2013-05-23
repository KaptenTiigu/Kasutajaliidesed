package Server;

import java.util.Iterator;

import Game.Player;
import Message.Message;

class Broadcaster extends Thread {
	private ActiveSessions activeSessions;
	private OutboundMessages outQueue;

	Broadcaster(ActiveSessions aa, OutboundMessages o) {
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
					
					if(cli.getName().equals(adressaat)) {
						cli.sendMessage(message);
						System.out.println("Saadan sonumit!");
					}
					if (adressaat == null) {
						cli.sendMessage(message);
					}
					if (!cli.isAlive()) {
						active.remove(); 		// ;-)
					}
				}
			}
		}
		
	}
}