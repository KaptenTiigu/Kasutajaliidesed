package Game;
import java.util.ArrayList;
import java.util.List;




public class Player {
	private String name;
	private List<Card> hand = new ArrayList<Card>();
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
	
	/**
	 * LIHTSALT TESTIMISE MEETODID
	 */
	public void killCard() {
		System.out.println(kill.getColor() + " " + kill.getValue());
	}
}
