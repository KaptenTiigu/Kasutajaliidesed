package Server;

import Server.ActiveSessions;
import Server.Broadcaster;
import Server.ClientSession;
import Server.OutboundMessages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
	private static final int PORT = 8888;
	private static int playerNumber =0;
	//private ArrayList<String> names = new ArrayList<String>(Arrays.asList("Player 1", "Player 2", "Player 3"));

	public static void main(String[] args) throws IOException {
		ActiveSessions activeSessions = new ActiveSessions(); //ÜHINE SESSIOONI VÄRK (MÄNGIJATE NIMEKIRI, JNE.)
		OutboundMessages outQueue = new OutboundMessages(); //SÕNUMITE SAATMINE JA HOIUSTAMINE FIFOS

		ServerSocket serv = new ServerSocket(PORT);
		System.out.println("Server startis...\n Kuulan porti:" + PORT);
		new Broadcaster(activeSessions, outQueue); // KONTROLLIB JA TEGUTSEB KLIENTI LEIDMISE JA DATA SAATMISEGA
		
		UnoGame game = new UnoGame(outQueue);
		try {
			while (true) { 									// serveri töötsükkel
				Socket sock = serv.accept(); 				// blocked!
				try {
					//uUE KLIENDI JAOKS UUE LÕIME TEGEMINE
					new ClientSession(sock, outQueue, activeSessions, ++playerNumber, game); // sh. ClientSession.start()
				} catch (IOException e) {
					System.out.println("Socketi loomise avarii :(");
					sock.close();
				}
			} 

		} finally {
			System.out.println("Server lõpetas");
			serv.close();
		}
	}
}
