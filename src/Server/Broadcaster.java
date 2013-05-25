package Server;

import java.io.IOException;
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
			//System.out.println("true");
			Message message = outQueue.getMessage(); 				// blocked
			//System.out.println("true: " + message);
			synchronized (activeSessions) { 				// ActiveSessions lukku!
				Iterator<ClientSession> active = activeSessions.iterator();			
				while (active.hasNext()) {
					ClientSession cli = active.next();
					String adressaat = message.getAdress();
						if (adressaat != null && !adressaat.isEmpty()) {
							if(cli.getName().equals(adressaat)) {
								cli.sendMessage(message);
								break;
							}
						} else {
								System.out.println("mustanahaline: " + message);
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