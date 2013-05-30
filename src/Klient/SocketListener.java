package Klient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;

import Message.Message;


class SocketListener extends Thread {
	private ObjectInputStream netIn;
	private Socket socket;
	private LinkedList<Message> inQueue;
	private boolean stopThread = false;
	
	public SocketListener(Socket socket, ObjectInputStream in, LinkedList<Message> inQueue) {
		this.netIn = in;
		this.socket = socket;
		this.inQueue = inQueue;
	}

	@Override
	public void run() {
		try {
			while (!stopThread) { 		
				Object fromServer = netIn.readObject(); 				// blocked...
				if (fromServer != null) {
					//System.out.println("UUS SÕNUM");
					Message message = (Message)fromServer;
					synchronized (inQueue) { 					// lukku!
						inQueue.add(message);		
						//if(!inQueue.isEmpty())System.out.println("UUS SÕNUM2");
					}
				}
			}
		} catch (IOException e) {//System.out.println("IOIOIOIOI");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!socket.isClosed()) {
					socket.close();
					System.out.println("closing...2");
					return;
				}
			} catch (IOException ee) {}
		} 
	}
	public LinkedList<Message> newMessages(){
		synchronized (inQueue) {
			return inQueue;
		}
	}
	/**
	 * Threadi peatamine.
	 */
	public void setStopThread() {
		stopThread = true;
	}
}
