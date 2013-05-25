package Server;

import java.util.LinkedList;

import Message.Message;

class OutboundMessages {
	private LinkedList<Message> messages = new LinkedList<Message>(); 				// Saatmata sõnumite FIFO

	public synchronized void addMessage(Message m) { 		// this lukku!
		messages.add(m);
		this.notifyAll(); 									// Broadcaster.notify()
	}
	public int messagesLength(){
		return messages.size();
	}
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