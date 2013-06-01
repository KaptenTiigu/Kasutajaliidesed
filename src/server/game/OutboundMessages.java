package server.game;

import java.util.LinkedList;

import yhiskasutatavad.Message;


/**
 * Siin hoiustatakse sõnumeid, mis vaja edasi saata.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class OutboundMessages {
	/**
	 * Saatmata sõnumite FIFO
	 */
	private LinkedList<Message> messages = new LinkedList<Message>(); 

	/**
	 * Sõnumi lisamine
	 * @param m sõnum
	 */
	public synchronized void addMessage(Message m) { 		// this lukku!
		messages.add(m);
		this.notifyAll(); 									// Broadcaster.notify()
	}
	/**
	 * Sõnumite koguse saamine
	 * @return sõnumite arv
	 */
	public int messagesLength(){
		return messages.size();
	}
	/**
	 * Sõnumi eemaldamine loendist
	 * @return sõnum
	 */
	public synchronized Message getMessage() { 
		try {
			while (messages.isEmpty() || messages==null) {
				this.wait(); 
			}			
		} catch (InterruptedException e) {}

		Message m = messages.getFirst();
		//System.out.println("sonu: " + m);
		messages.removeFirst();
		
		return m;
	}
}