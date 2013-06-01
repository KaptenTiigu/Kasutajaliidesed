package server.messages;

import java.util.ArrayList;
import java.util.List;

import server.game.ClientSession;

import klient.Client;

import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;


/**
 * Sõnum milles server saadab ühele mängijale ülesvõetavad kaardid.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class PickUpCardsMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ülesvõetavad kaardid
	 */
	private List<Card> kaardid;
	/**
	 * Mängija nimi, kellele sõnum saadetakse
	 */
	private String address;
	
	public PickUpCardsMessage(List<Card> kaardid, String address) {
		this.kaardid = kaardid;
		this.address = address;
	}
	@Override
	public void onReceive(ClientSession s) {	
	}

	@Override
	public void onReceive(Client c) {
		Player p = c.getPlayer();
		for (Card card : kaardid) {
			p.pickupCard(card);
			System.out.println(p.getName()+">> Võtsin kätte:" + card.getName());
		}
		c.changeUIhand();
	}

	@Override
	public String getAdress() {
		return address;
	}

}
