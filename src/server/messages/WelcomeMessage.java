package server.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;

/**
 * Serveriga esmakordselt �hinedes saadetav s�num
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class WelcomeMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * M�ngija nimi
	 */
	private String playerName;
	
	/**
	 * S�numi tegemine
	 * @param name m�ngija nimi
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
		System.out.println("Olen m�ngija " + p.getName());
		
	}
	
	@Override
	public String getAdress() {
		return playerName;
	}
}