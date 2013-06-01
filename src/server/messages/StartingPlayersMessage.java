package server.messages;

import java.util.ArrayList;
import java.util.List;

import server.game.ClientSession;

import klient.Client;

import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;


/**
 * Sõnum, milles saadab server kliendile, mängust osavõtvate mängijate nimed.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class StartingPlayersMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Mängijate nimed
	 */
	private List<String> mangijad = new ArrayList<String>();
	/**
	 * Järjekord
	 */
	private String whoseTurn;

	/**
	 * SÕnumi koostamine
	 * @param mangijad mängijate nimed
	 * @param whoseTurn järjekord
	 */
	public StartingPlayersMessage(List<String> mangijad, String whoseTurn) {
		this.mangijad = mangijad;
		this.whoseTurn = whoseTurn;
	}
	
	@Override
	public void onReceive(ClientSession s) {	
	}

	@Override
	public void onReceive(Client c) {
		Player p = c.getPlayer();
		System.out.println("startingPlayersMessage kliendis");
		p.addPlayers(mangijad);
		System.out.println("startingPlayersMessage kliendis tehtud");
		p.teisedMangijad();
		if(p.getName().equals(whoseTurn)) {
			p.setPermission(true);
		} else {
			p.setPermission(false);
		}
		p.startGame();
		
	}

	@Override
	public String getAdress() {
		return null;
	}

}