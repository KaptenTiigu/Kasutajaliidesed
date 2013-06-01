package klient.messages;

import java.util.List;

import server.game.ClientSession;

import klient.Client;

import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;


public class ClientPickUpMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		s.pickUpOneCard();
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
