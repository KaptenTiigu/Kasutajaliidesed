package server.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;

/**
 * Sõnum, mis saadetakse serveripooolt kui on selgunud võitja.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class GameWinMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Käidud kaart
	 */
	private Card kaart;
	/**
	 * Võitja nimi
	 */
	private String winner;
	
	public GameWinMessage(Card card, String winner) {
		this.winner = winner;
		kaart = card;
	}
	@Override
	public void onReceive(ClientSession s) {	
	}

	@Override
	public void onReceive(Client c) {
		Player p = c.getPlayer();
		p.addKillCard(kaart);
		p.setPermission(false);
		System.out.println("GAME OVER - VÕTIS: "+ winner.toUpperCase());
		c.gameWinner(winner);
	}

	@Override
	public String getAdress() {
		return null;
	}

}
