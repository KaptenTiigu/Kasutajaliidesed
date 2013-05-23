package Klient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import Game.Player;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 8888;
		Player player;
		Socket socket;
		BufferedReader netIn;
		LinkedList<String> inQueue = new LinkedList<String>(); // FIFO
		InetAddress servAddr = InetAddress.getByName("localhost");

		try {
			socket = new Socket(servAddr, port); 				// JabberServer.PORT
		} catch (IOException e) {
			System.out.println("Sry - server pole n�htav :(((");
			return;
		}
		try {
			System.out.println("socket = " + socket);
			
			// Sokli sisend-v�ljund:
			netIn = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()));
			PrintWriter netOut = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(
										socket.getOutputStream())), true);
			
			// Klaviatuurisisend:
			BufferedReader stdin = new BufferedReader(
					new InputStreamReader(System.in));

			// Saabunud s�numite kuulaja:
			SocketListener l = new SocketListener(socket, netIn, inQueue);
			l.start();

			netOut.println(myName); // saadame oma nime serverisse
			
			String msg;

			do { // JabberClient eluts�kli p�hiosa **********************
				System.out.println("Sisesta s�num ja vajuta ENTER; l�petamiseks toksi END: ");
				System.out.println("Saabunud s�numite lugemiseks vajuta ENTER");
				
				msg = stdin.readLine(); 						// blocked...
				// System.out.println( "Kuulaja olek = " + k.isAlive() ); 	// debugging...
				
				if (msg.length() > 0)
					netOut.println(msg); 						// saadame oma s�numi serverisse
				
				if (!inQueue.isEmpty()) { 						// kas on midagi saabunud?
					synchronized (inQueue) { 					// lukku !!!
						Iterator<String> incoming = inQueue.iterator();
						while (incoming.hasNext()) {
							System.out.println(">> " + incoming.next());
							incoming.remove();
						}
					}
				}
			} while (!msg.equals("END")); // END l�petab kliendi t��

		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}

}