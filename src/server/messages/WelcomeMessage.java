package server.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;

/**
 * Serveriga esmakordselt ühinedes saadetav sõnum
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class WelcomeMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Mängija nimi
	 */
	private String playerName;
	
	/**
	 * Sõnumi tegemine
	 * @param name mängija nimi
	 */
	public WelcomeMessage (String name){
		playerName = name;
	}

	@Override
	public void onReceive(ClientSession s) {
	}

	@Override
	public void onReceive(Client c) {
		Player p = new Player(playerName);
		c.makeUserInterFace();
		c.setPlayer(p);
		System.out.println("Olen mängija " + p.getName());
		
	}
	
	@Override
	public String getAdress() {
		return playerName;
	}
}