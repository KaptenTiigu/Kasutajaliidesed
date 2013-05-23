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

import Game.Player;
import Message.Message;


public class Client {

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
		Player player;

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
			
			/*// Klaviatuurisisend:
			BufferedReader stdin = new BufferedReader(
					new InputStreamReader(System.in));*/

			// Saabunud sõnumite kuulaja:
			SocketListener l = new SocketListener(socket, netIn, inQueue);
			l.start();
			
			do { 
				if (!inQueue.isEmpty()) { 	// kas on midagi saabunud?
					System.out.println("pole tyhi");
					synchronized (inQueue) { 					// lukku !!!
						Iterator<Message> incoming = inQueue.iterator();
						while (incoming.hasNext()) {
							System.out.println(">> " + incoming.next());
							//tee midagi sõnumiga
							System.out.println("uus sõnum");
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

}