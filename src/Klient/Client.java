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
			System.out.println("Sry - server pole n�htav :(((");
			return;
		}
		try {
			System.out.println("socket = " + socket);
			
			// Sokli sisend-v�ljund:
			netIn = new ObjectInputStream(
					socket.getInputStream());
			netOut = new ObjectOutputStream(
					socket.getOutputStream());
			
			/*// Klaviatuurisisend:
			BufferedReader stdin = new BufferedReader(
					new InputStreamReader(System.in));*/

			// Saabunud s�numite kuulaja:
			SocketListener l = new SocketListener(socket, netIn, inQueue);
			l.start();
			
			do { // JabberClient eluts�kli p�hiosa **********************
				/*System.out.println("Sisesta s�num ja vajuta ENTER; l�petamiseks toksi END: ");
				System.out.println("Saabunud s�numite lugemiseks vajuta ENTER");
				
				msg = stdin.readLine(); 						// blocked...
				// System.out.println( "Kuulaja olek = " + k.isAlive() ); 	// debugging...
				
				if (msg.length() > 0)
					netOut.println(msg); 						// saadame oma s�numi serverisse
				*/
				if (!inQueue.isEmpty()) { 						// kas on midagi saabunud?
					synchronized (inQueue) { 					// lukku !!!
						Iterator<Message> incoming = inQueue.iterator();
						while (incoming.hasNext()) {
							System.out.println(">> " + incoming.next());
							//tee midagi s�numiga
							
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