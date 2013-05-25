package Message.Server;

import java.util.ArrayList;
import java.util.List;

import Game.Card;
import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

/**
 * S�num, milles saadab seerver kliendile, m�ngust osav�tvate m�ngijate nimed.
 * @author LehoRaiguma
 *
 */
public class StartingPlayersMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> mangijad = new ArrayList<String>();
	private String whoseTurn;

	public StartingPlayersMessage(List<String> mangijad, String whoseTurn) {
		this.mangijad = mangijad;
		this.whoseTurn = whoseTurn;
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
		if(c.getName().equals(whoseTurn)) {
			c.setPermission(true);
		} else {
			c.setPermission(false);
		}
		
	}

	@Override
	public String getAdress() {
		//System.out.println("startingPlayersMessage");
		return null;
	}

}