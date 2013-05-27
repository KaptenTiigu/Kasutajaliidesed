package Message.Server;

import java.util.ArrayList;
import java.util.List;

import Game.Card;
import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

/**
 * Sõnum, milles saadab seerver kliendile, mängust osavõtvate mängijate nimed.
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
		
	}

	@Override
	public String getAdress() {
		//System.out.println("startingPlayersMessage");
		return null;
	}

}