package Message.Server;

import Game.Card;
import Game.Card.Color;
import Game.Player;
import Klient.Client;
import Message.Message;
import Server.ClientSession;

/**
 * Sõnum, milles saadab server kliendile kaardi ja järjekorra.
 * @author LehoRaiguma
 *
 */
public class ServerCardMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card kaart;
	private String address;
	private Card.Color varv;
	private String jargmine;

	public ServerCardMessage(String next, Card card, Color color) {
		// TODO Auto-generated constructor stub
		this.kaart = card;
		//this.address = address;
		this.varv = color;
		this.jargmine = next;
	}

	public ServerCardMessage(/*String address, */String jargmine, Card kaart) {
		this.kaart = kaart;
		//this.address = address;
		this.jargmine = jargmine;
	}
	
	@Override
	public void onReceive(ClientSession s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Client c) {
		Player p = c.getPlayer();
		//if (varv != null) {
			p.addColor(varv);
			System.out.println("VÄRV ON: "+ varv);
		//}
		if (jargmine.equals(p.getName())) {
			p.setPermission(true);
		} else {
			p.setPermission(false);
		}
		p.addKillCard(kaart);
			//c.killCard();
	}

	@Override
	public String getAdress() {
		// TODO Auto-generated method stub
		return null;
	}

}
