package klient.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Card;
import yhiskasutatavad.Message;

public class NewGameMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	@Override
	public void onReceive(ClientSession s) {
		/**
		 * server koostab uue sõnumi ja saadab kaardid kõigile teistele.
		 */
		System.out.println("SERVER SAI SÕNUMI KÄTTE");
		s.newGame();
	}

	@Override
	public void onReceive(Client c) {
	}

	@Override
	public String getAdress() {
		return null;
	}

}
