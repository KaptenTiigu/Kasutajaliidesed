package Message;

import java.io.Serializable;
import java.util.List;

import Game.Card;
import Game.Player;
import Klient.Client;
import Server.ClientSession;


enum MessageType {
	TEXT, CLIENTCARD, SERVERCARD
}

public interface Message extends Serializable {
	/*MessageType getMessageType();
	String getContents();
	Card getPlayerCard();
	Card getKillCard();
	List<Card> getPickUpCards();
	Player whoseTurn();
	Player getAdress();*/
	/**
	 * Kui klient v�tab serveri s�numi
	 * 
	 * @param s - CLientSession
	 */
	void onReceive(ClientSession s);
	/**
	 * Kui server saab kliendi s�numi vastu
	 * @param c
	 */
	void onReceive(Client c);
	/**
	 * Meetod tagastab adressaadi.
	 * @return aadress, kellele s�nu saadetakse
	 */
	String getAdress();
}
