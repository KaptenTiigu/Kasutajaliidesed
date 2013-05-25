package Message.Server;

import java.util.ArrayList;
import java.util.List;

import Game.Card;
import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

/**
 * S�num milles server saadab �hele m�ngijale �lesv�etavad kaardid.
 * @author LehoRaiguma
 *
 */
public class PickUpCardsMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Card> kaardid;
	private String address;
	
	public PickUpCardsMessage(List<Card> kaardid, String address) {
		this.kaardid = kaardid;
		this.address = address;
	}
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Player c) {
		for (Card card : kaardid) {
			c.pickupCard(card);
			//TESTIMISE EESM�RGIL
			System.out.println(c.getName()+">> V�tsin k�tte:" + card.getName());
		}
		//TESTIMISE EESM�RGIL
		//c.kaartidePrint();
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return address;
	}

}
