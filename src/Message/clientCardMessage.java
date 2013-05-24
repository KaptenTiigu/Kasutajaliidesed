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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card kaart;
	private Card.Color varv;
	
	public clientCardMessage(Card kaart) {
		this.kaart = kaart;
	}
	
	public clientCardMessage(Card kaart, Card.Color varv) {
		this.kaart = kaart;
		this.varv = varv;
	}

	@Override
	public void onReceive(ClientSession s) {
		/**
		 * server koostab uue sõnumi ja saadab kaardid kõigile teistele.
		 */
		System.out.println("SERVER SAI SÕNUMI KÄTTE");
		Message message = s.makeMessage(kaart, varv);
		s.addMessage(message);
		//s.outQueue.addMessage(msg);
	}

	@Override
	public void onReceive(Player c) {
		//c.Player = new Player("maksim");
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return null;
	}

}
