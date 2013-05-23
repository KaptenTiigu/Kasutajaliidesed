package Message;

import Game.Card;
import Game.Card.Color;
import Game.Player;
import Klient.Client;
import Server.ClientSession;

/**
 * Sõnum, milles saadab server kliendile kaardi ja järjekorra.
 * @author LehoRaiguma
 *
 */
public class serverCardMessage implements Message {
	private Card kaart;
	private String address;
	private Card.Color varv;
	private String jargmine;

	public serverCardMessage(String next, Card card, Color color) {
		// TODO Auto-generated constructor stub
		this.kaart = card;
		//this.address = address;
		this.varv = color;
		this.jargmine = next;
	}

	/*public serverCardMessage(String next, Card card) {
		// TODO Auto-generated constructor stub
	}*/

	public serverCardMessage(/*String address, */String jargmine, Card kaart) {
		this.kaart = kaart;
		//this.address = address;
		this.jargmine = jargmine;
	}
	/*
	public void serverCardMessage(String jargmine, Card kaart, Card.Color varv) {
		this.kaart = kaart;
		//this.address = address;
		this.varv = varv;
		this.jargmine = jargmine;
	}*/
	
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Client c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return null;
	}

}
