package Game;
import java.util.ArrayList;
import java.util.List;




/**
 * Klass, mis sisaldab endas mängu mängija poole loogikat.
 * @author LehoRaiguma
 *
 */
public class Player {
	/**
	 * Mängija nimi
	 */
	private String name;
	/**
	 * Mängijal käes olevad kaardid
	 */
	private List<Card> hand = new ArrayList<Card>();
	/**
	 * Teised mängus olevad mängijad
	 */
	private List<String> players = new ArrayList<String>();
	/**
	 * Pile pealmine kaart, kaart mida tuleb tappa
	 */
	private Card kill;
	/**
	 * Värv, mis värvi kaarti tohib panna
	 */
	private Card.Color specialColor;
	/**
	 * Kas on mängija kord käia.
	 */
	private boolean permission = false;

	/**
	 * Mängijat luues antakse talle nimi
	 * @param name - mängija nimi
	 */
	public Player(String name) {
		this.name = name;
	}
	
	/**
	 * Nime tagastamine
	 * @return nimi
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Tapetava kaardi muutmine
	 * @param card - tapetav kaart
	 */
	public void addKillCard(Card card) {
		if(kill == null) {
			kill = card;
			System.out.println("Lauale käidi "+kill.getColor() + " " + kill.getValue());
			if(permission)System.out.println("------------------------SINU KÄIK!------------------------");
			else System.out.println("------------------------VASTASE KÄIK!------------------------");
		}else if(!kill.getName().equals(card.getName())) {
			kill = card;
			System.out.println("Lauale käidi "+kill.getColor() + " " + kill.getValue());
			if(permission)System.out.println("------------------------SINU KÄIK!------------------------");
			else System.out.println("------------------------VASTASE KÄIK!------------------------");
		} else {
			System.out.println("Eelmine mängija võttis kaardi");
			System.out.println("Tapetav kaart" + kill.getName());
			if(permission)System.out.println("------------------------SINU KÄIK!------------------------");
			else System.out.println("------------------------VASTASE KÄIK!------------------------");
		}
		
	}
	
	/**
	 * Värvi lisamine
	 * @param color - värv
	 */
	public void addColor(Card.Color color) {
		specialColor = color;
	}
	
	/**
	 * Mängija käib kaardi
	 * @param card - kaart
	 * @return - kaart
	 */
	public Card playCard(Card card) {
		hand.remove(card);
		return card;
	}

	/**
	 * Mängija kätte kaartide lisamine
	 * @param card - kaart
	 */
	public void pickupCard(Card card) {
		hand.add(card);
	}

	/**
	 * Mängija valib värvi
	 * @param color - värv
	 * @return - värv
	 */
	public Card.Color chooseColor(Card.Color color) {
		///kasutaja valib
		return color;
	}

	/**
	 * Mängija käimisõigsuse määramine
	 * @param permission  true - kui on õigus, false - kui puudub õigus
	 */
	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	
	/**
	 * Mängija käimisõigsuse tagastamine.
	 */
	public boolean getPermission() {
		return permission;
	}
	
	/**
	 * Käes olevate kaartide tagastamine
	 * @return käes olevad kaardid
	 */
	public List<Card> getCards() {
		return hand;
	}
	public Card getKillCard() {
		return kill;
	}
	public Card getCardByName(String string) {
		for(Card a : hand) {
			if(string.equals(a.getName())) return a;
		}
		return null;
	}
	/**
	 * Teiste mängijate lisamine
	 * @param serverPlayers - teiste mängijate list
	 */
	public void addPlayers(List<String> serverPlayers) {
		for(String a : serverPlayers) {
			if(!a.equals(name))players.add(a);
		}
	}
	
	/**
	 * Unogames võrdlemiseks, kui nimed on samad siis on võrdsed
	 * @param player
	 * @return
	 */
	public boolean equals(Player player) {
		if(this.getName() == name)return true;
		return false;
	}
	/**
	 * LIHTSALT TESTIMISE MEETODID
	 */
	//prindib killcardi
	public void killCard() {
		System.out.println("Lauale käidi "+kill.getColor() + " " + kill.getValue());
		if(permission)System.out.println("------------------------SINU KÄIK!------------------------");
		else System.out.println("------------------------VASTASE KÄIK!------------------------");
	}
	//prindib käeasolevad kaardid
	public void kaartidePrint() {
		for (Card a : hand) {
			System.out.println(name + ": " + a.getColor() + " " + a.getValue());
		}
	}
	//tagastab teised mängijad
	public void teisedMangijad() {
		System.out.println(name +">>Teised mängijad on:" + players);
	}
}
