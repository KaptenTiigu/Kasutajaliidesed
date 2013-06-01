package server;


import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import server.game.ActiveSessions;
import server.game.Broadcaster;
import server.game.ClientSession;
import server.game.OutboundMessages;
import server.game.UnoGame;

public class Server {
	/**
	 * Pordi number
	 */
	private static final int PORT = 8888;
	/**
	 * Mängija unikaalne number
	 */
	private static int playerNumber = 0;
	/**
	 * Mängijate aktiivsete sessioonide kogum (mängu kohta)
	 */
	private ActiveSessions activeSessions = new ActiveSessions();
	/**
	 * Sõnumite hoiustamine
	 */
	private OutboundMessages outQueue = new OutboundMessages();
	/**
	 * Serveri socket
	 */
	private ServerSocket serv;
	/**
	 * Uno mängu objekt
	 */
	private UnoGame game = new UnoGame(outQueue);
	/**
	 * Mänguga liitumise lukk
	 */
	private String lock = "lukustatud";
    //new Broadcaster(activeSessions, outQueue); // KONTROLLIB JA TEGUTSEB KLIENTI LEIDMISE JA DATA SAATMISEGA
    /**
     * Serveri loomise pannakse ta socketit kuulama.
     */
    public Server() {
    	listenSocket();
    }
    /**
     * Uute klientide vastuvõtmine ja neid mängudele suunamine
     */
    public void listenSocket() { 	
		try {
			serv = new ServerSocket(PORT);
	    	System.out.println("Server startis...\n Kuulan porti:" + PORT);
	    	new Broadcaster(activeSessions, outQueue);
			while (true) { 									// serveri töötsükkel
				Socket sock = serv.accept(); 				// blocked!
				try {
					synchronized(lock) {
					if(!game.joinExistingGame()) {
						System.out.println("Vanas mängus kohad täis, teen uue mängu");
						activeSessions = new ActiveSessions();
						outQueue = new OutboundMessages();
						new Broadcaster(activeSessions, outQueue);
						game = new UnoGame(outQueue);
					}
					//uUE KLIENDI JAOKS UUE LÕIME TEGEMINE
					new ClientSession(sock, outQueue, activeSessions, ++playerNumber, game, this); // sh. ClientSession.start()
					}
				} catch (IOException e) {
					System.out.println("Socketi loomise avarii :(");
					sock.close();
				}
			} 

		} catch (BindException err) {
			System.out.println("Sellise aadressiga server juba käib");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			System.out.println("Server lõpetas");
			game.getWriter().closeWriter();
			try {
				serv.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {}
		}
    }
    /**
     * Uue mänguga ühinemine kui on olemas ClientSession
     * @param sess ClientSession
     */
    public void newGame(ClientSession sess) {
    	synchronized(lock) {
    	if(!game.joinExistingGame()) {
			System.out.println("Vanas mängus kohad täis, teen uue mängu");
			activeSessions = new ActiveSessions();
			outQueue = new OutboundMessages();
			new Broadcaster(activeSessions, outQueue);
			game = new UnoGame(outQueue);
		}
    	System.out.println("rejoining");
    	}
    	sess.startNewGame(outQueue, activeSessions, game);
    	
    }
}
