package Server;

import java.util.LinkedList;

import Message.Message;

class OutboundMessages {
	private LinkedList<Message> messages = new LinkedList<Message>(); 				// Saatmata sõnumite FIFO

	public synchronized void addMessage(Message m) { 		// this lukku!
		messages.add(m);
		this.notifyAll(); 									// Broadcaster.notify()
	}

	public synchronized Message getMessage() { 
		try {
			//System.out.println(messages);
			while (messages.isEmpty())
				this.wait(); 
		} catch (InterruptedException e) {}

		Message m = messages.getFirst();
		messages.removeFirst();
		
		return m;
	}
}