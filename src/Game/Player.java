package Game;
import java.util.ArrayList;
import java.util.List;




public class Player {
	private String name;
	private List<Card> hand = new ArrayList<Card>();
	private List<String> players = new ArrayList<String>();
	private Card kill;
	private Card.Color specialColor;
	private boolean permission = false;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addKillCard(Card card) {
		kill = card;
	}
	
	public void addColor(Card.Color color) {
		specialColor = color;
	}
	
	public Card playCard(Card card) {
		hand.remove(card);
		return card;
	}

	public void pickupCard(Card card) {
		hand.add(card);
	}

	public Card.Color chooseColor(Card.Color color) {
		///kasutaja valib
		return color;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	
	public List<Card> getCards() {
		return hand;
	}
	
	public void addPlayers(List<Player> serverPlayers) {
		for(Player a : serverPlayers) {
			if(!a.getName().equals(name))players.add(a.getName());
		}
	}
	
	/**
	 * LIHTSALT TESTIMISE MEETODID
	 */
	//prindib killcardi
	public void killCard() {
		System.out.println(kill.getColor() + " " + kill.getValue());
	}
	//prindib käeasolevad kaardid
	public void kaartidePrint() {
		for (Card a : hand) {
			System.out.println(name + ": " + a.getColor() + " " + a.getValue());
		}
	}
	
	public void teisedMangijad() {
		System.out.println("Teised mängijad on:" + players);
	}
}
