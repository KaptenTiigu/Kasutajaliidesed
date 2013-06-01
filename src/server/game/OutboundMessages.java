package server.game;

import java.util.LinkedList;

import yhiskasutatavad.Message;


/**
 * Siin hoiustatakse s�numeid, mis vaja edasi saata.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class OutboundMessages {
	/**
	 * Saatmata s�numite FIFO
	 */
	private LinkedList<Message> messages = new LinkedList<Message>(); 

	/**
	 * S�numi lisamine
	 * @param m s�num
	 */
	public synchronized void addMessage(Message m) { 		// this lukku!
		messages.add(m);
		this.notifyAll(); 									// Broadcaster.notify()
	}
	/**
	 * S�numite koguse saamine
	 * @return s�numite arv
	 */
	public int messagesLength(){
		return messages.size();
	}
	/**
	 * S�numi eemaldamine loendist
	 * @return s�num
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