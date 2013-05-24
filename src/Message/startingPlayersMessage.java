package Message;

import java.util.ArrayList;
import java.util.List;

import Game.Card;
import Game.Player;
import Klient.Client;
import Server.ClientSession;

/**
 * S�num, milles saadab seerver kliendile, m�ngust osav�tvate m�ngijate nimed.
 * @author LehoRaiguma
 *
 */
public class startingPlayersMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Player> mangijad = new ArrayList<Player>();

	public startingPlayersMessage(List<Player> mangijad) {
		this.mangijad = mangijad;
	}
	
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Player c) {
		System.out.println("startingPlayersMessage kliendis");
		c.addPlayers(mangijad);
		System.out.println("startingPlayersMessage kliendis tehtud");
		c.teisedMangijad();
		/**
		 * Lisab m�ngijate nimed kusagile kliendi/player objektis,
		 */
		
	}

	@Override
	public String getAdress() {
		System.out.println("startingPlayersMessage");
		return null;
	}

}
