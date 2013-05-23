package Message;

import Game.Card;
import Game.Player;
import Klient.Client;
import Server.ClientSession;

/**
 * Sõnum, milles saadab klient kaardi serverile
 * @author LehoRaiguma
 *
 */
public class clientCardMessage implements Message {
	private Card kaart;
	private Card.Color varv;
	
	public void ServerCardMessage(Card kaart) {
		this.kaart = kaart;
	}
	
	public void ServerCardMessage(Card kaart, Card.Color varv) {
		this.kaart = kaart;
		this.varv = varv;
	}

	@Override
	public void onReceive(ClientSession s) {
		/**
		 * server koostab uue sõnumi ja saadab kaardid kõigile teistele.
		 */
		Message message = s.makeMessage(kaart, varv);
		s.sendMessage(message);
	}

	@Override
	public void onReceive(Client c) {
		
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return null;
	}

}
