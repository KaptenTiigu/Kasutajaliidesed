package Message.Client;

import java.util.List;

import Game.Card;
import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

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
