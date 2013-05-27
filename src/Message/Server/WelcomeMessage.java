package Message.Server;

import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

public class WelcomeMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playerName;
	
	public WelcomeMessage (String name){
		playerName = name;
	}

	@Override
	public void onReceive(ClientSession s) {
	}

	@Override
	public String getAdress() {
		return playerName;
	}

	@Override
	public void onReceive(Client c) {
		Player p = new Player(playerName);
		c.setPlayer(p);
		System.out.println("Olen mängija " + p.getName());
	}
}