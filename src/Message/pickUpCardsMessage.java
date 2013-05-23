package Message;

import java.util.ArrayList;
import java.util.List;

import Game.Card;
import Klient.Client;
import Server.ClientSession;

/**
 * Sõnum milles server saadab ühele mängijale ülesvõetavad kaardid.
 * @author LehoRaiguma
 *
 */
public class pickUpCardsMessage implements Message {
	private List<Card> kaardid = new ArrayList<Card>();
	private String address;
	
	public pickUpCardsMessage(List<Card> kaardid, String address) {
		this.kaardid = kaardid;
		this.address = address;
	}
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Client c) {
		/**
		 * annab kaardid mängijale.
		 */
		
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return address;
	}

}
