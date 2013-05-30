package Message.Server;

import Game.Card;
import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

public class GameWinMessage implements Message {
	private Card kaart;
	private String winner;
	
	public GameWinMessage(Card card, String winner) {
		this.winner = winner;
		kaart = card;
	}
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Client c) {
		Player p = c.getPlayer();
		//if (varv != null) {
			//p.addColor(varv);
			//System.out.println("VÄRV ON: "+ varv);
		//}
		p.addKillCard(kaart);
		p.setPermission(false);
		System.out.println("GAME OVER - VÕTIS: "+ winner.toUpperCase());
		c.setStopThread();
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return null;
	}

}
