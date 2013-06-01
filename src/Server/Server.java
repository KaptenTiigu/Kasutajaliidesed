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
	 * M�ngija unikaalne number
	 */
	private static int playerNumber = 0;
	/**
	 * M�ngijate aktiivsete sessioonide kogum (m�ngu kohta)
	 */
	private ActiveSessions activeSessions = new ActiveSessions();
	/**
	 * S�numite hoiustamine
	 */
	private OutboundMessages outQueue = new OutboundMessages();
	/**
	 * Serveri socket
	 */
	private ServerSocket serv;
	/**
	 * Uno m�ngu objekt
	 */
	private UnoGame game = new UnoGame(outQueue);
	/**
	 * M�nguga liitumise lukk
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
     * Uute klientide vastuv�tmine ja neid m�ngudele suunamine
     */
    public void listenSocket() { 	
		try {
			serv = new ServerSocket(PORT);
	    	System.out.println("Server startis...\n Kuulan porti:" + PORT);
	    	new Broadcaster(activeSessions, outQueue);
			while (true) { 									// serveri t��ts�kkel
				Socket sock = serv.accept(); 				// blocked!
				try {
					synchronized(lock) {
					if(!game.joinExistingGame()) {
						System.out.println("Vanas m�ngus kohad t�is, teen uue m�ngu");
						activeSessions = new ActiveSessions();
						outQueue = new OutboundMessages();
						new Broadcaster(activeSessions, outQueue);
						game = new UnoGame(outQueue);
					}
					//uUE KLIENDI JAOKS UUE L�IME TEGEMINE
					new ClientSession(sock, outQueue, activeSessions, ++playerNumber, game, this); // sh. ClientSession.start()
					}
				} catch (IOException e) {
					System.out.println("Socketi loomise avarii :(");
					sock.close();
				}
			} 

		} catch (BindException err) {
			System.out.println("Sellise aadressiga server juba k�ib");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			System.out.println("Server l�petas");
			game.getWriter().closeWriter();
			try {
				serv.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {}
		}
    }
    /**
     * Uue m�nguga �hinemine kui on olemas ClientSession
     * @param sess ClientSession
     */
    public void newGame(ClientSession sess) {
    	synchronized(lock) {
    	if(!game.joinExistingGame()) {
			System.out.println("Vanas m�ngus kohad t�is, teen uue m�ngu");
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
