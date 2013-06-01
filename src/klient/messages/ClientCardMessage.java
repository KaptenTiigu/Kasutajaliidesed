package klient.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;

/**
 * S�num, milles saadab klient kaardi serverile
 * @author LehoRaiguma
 *
 */
public class ClientCardMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card kaart;
	private Card.Color varv;
	
	public ClientCardMessage(Card kaart) {
		this.kaart = kaart;
	}
	
	public ClientCardMessage(Card kaart, Card.Color varv) {
		this.kaart = kaart;
		this.varv = varv;
		System.out.println("V�rv s�numis: " + varv);
	}

	@Override
	public void onReceive(ClientSession s) {
		/**
		 * server koostab uue s�numi ja saadab kaardid k�igile teistele.
		 */
		System.out.println("SERVER SAI S�NUMI K�TTE");
		s.recieveClientCard(kaart, varv);
		/*Message message = s.makeMessage(kaart, varv);
		s.addMessage(message);*/
		//s.outQueue.addMessage(msg);
	}

	@Override
	public void onReceive(Client c) {
		//c.Player = new Player("maksim");
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return null;
	}

}
