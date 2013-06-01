package server.messages;

import java.util.List;

import server.game.ClientSession;

import klient.Client;

import yhiskasutatavad.Card;
import yhiskasutatavad.Message;
import yhiskasutatavad.Player;
import yhiskasutatavad.Card.Color;


/**
 * Sõnum, milles saadab server kliendile kaardi ja järjekorra.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class ServerCardMessage implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Saadetav kaart
	 */
	private Card kaart;
	/**
	 * Saadetav värv
	 */
	private Card.Color varv;
	/**
	 * Saadetav järgmise playeri nimi
	 */
	private String jargmine;
	/**
	 * Mängijate nimekiri
	 */
	private List<Player> players;
	
	/**
	 * Sõnumi moodustamine
	 * @param next järgmise playeri nimi
	 * @param card Saadetav kaart
	 * @param color Saadetav värv
	 * @param players Mängijate nimekiri
	 */
	public ServerCardMessage(String next, Card card, Color color, List<Player> players) {
		this.kaart = card;
		this.varv = color;
		this.jargmine = next;
		this.players = players;
	}

	@Override
	public void onReceive(ClientSession s) {	
	}

	@Override
	public void onReceive(Client c) {
		Player p = c.getPlayer();
			p.addColor(varv);
			System.out.println("VÄRV ON: "+ varv);
		if (jargmine.equals(p.getName())) {
			p.setPermission(true);
		} else {
			p.setPermission(false);
		}
		p.addKillCard(kaart);
		if(kaart!=null)c.addKillCard(kaart.getName(), players);
		else c.addKillCard(null, players);
	}

	@Override
	public String getAdress() {
		return null;
	}

}
