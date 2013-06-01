package yhiskasutatavad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import klient.gui.GUI;


/**
 * Klass, mis sisaldab endas m�ngu m�ngija poole loogikat.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 *
 */
public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * M�ngija nimi
	 */
	private String name;
	/**
	 * M�ngijal k�es olevad kaardid
	 */
	private List<Card> hand = new ArrayList<Card>();
	/**
	 * Teised m�ngus olevad m�ngijad
	 */
	private List<String> players = new ArrayList<String>();
	/**
	 * Pile pealmine kaart, kaart mida tuleb tappa
	 */
	private Card kill;
	/**
	 * V�rv, mis v�rvi kaarti tohib panna
	 */
	private Card.Color killColor = Card.Color.NONE;
	/**
	 * Kas on m�ngija kord k�ia.
	 */
	private boolean permission = false;
	
	private GUI userInterFace;
	/**
	 * M�ngijat luues antakse talle nimi
	 * @param name - m�ngija nimi
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
	
	public void setUserInterFace(GUI userInterFace) {
		this.userInterFace = userInterFace;
	}
	
	public void startGame() {
		userInterFace.startGame(players, hand, 5);
		System.out.println("START GAME ");
	}
	/**
	 * Tapetava kaardi muutmine
	 * @param card - tapetav kaart
	 */
	public void addKillCard(Card card) {
		if(kill == null) {
			kill = card;
			if(kill!=null)System.out.println("Lauale k�idi "+kill.getColor() + " " + kill.getValue());
			else System.out.println("Lauale ei k�idud midagi, v�eti pakist kaart");
			if(killColor!=Card.Color.NONE)System.out.println("Pead k�ima kaardi, mille v�rv on: " + killColor);
			if(permission)System.out.println("------------------------SINU K�IK!------------------------");
			else System.out.println("------------------------VASTASE K�IK!------------------------");
		}else if(!kill.getName().equals(card.getName())) {
			kill = card;
			
			System.out.println("Lauale k�idi "+kill.getColor() + " " + kill.getValue());
			if(killColor!=Card.Color.NONE)System.out.println("Pead k�ima kaardi, mille v�rv on: " + killColor);
			if(permission)System.out.println("------------------------SINU K�IK!------------------------");
			else System.out.println("------------------------VASTASE K�IK!------------------------");
		} else {
			System.out.println("Eelmine m�ngija v�ttis kaardi");
			System.out.println("Tapetav kaart" + kill.getName());
			if(killColor!=Card.Color.NONE)System.out.println("Pead k�ima kaardi, mille v�rv on: " + killColor);
			if(permission)System.out.println("------------------------SINU K�IK!------------------------");
			else System.out.println("------------------------VASTASE K�IK!------------------------");
		}
		
	}
	
	/**
	 * V�rvi lisamine
	 * @param color - v�rv
	 */
	public void addColor(Card.Color color) {
		killColor = color;
	}
	/**
	 * V�rvi tagastamine
	 * @param color - v�rv
	 */
	public Card.Color getColor() {
		return killColor;
	}
	
	/**
	 * M�ngija k�ib kaardi
	 * @param card - kaart
	 * @return - kaart
	 */
	public Card playCard(Card card) {
		hand.remove(card);
		return card;
	}
	
	public void resetHand() {
		hand.clear();
		kill = null;
		killColor = Card.Color.NONE;
		players.clear();
	}
	/**
	 * M�ngija k�tte kaartide lisamine
	 * @param card - kaart
	 */
	public void pickupCard(Card card) {
		hand.add(card);
	}

	/**
	 * M�ngija valib v�rvi
	 * @param color - v�rv
	 * @return - v�rv
	 */
	public Card.Color chooseColor(Card.Color color) {
		///kasutaja valib
		return color;
	}

	/**
	 * M�ngija k�imis�igsuse m��ramine
	 * @param permission  true - kui on �igus, false - kui puudub �igus
	 */
	public void setPermission(boolean permission) {
		this.permission = permission;
	}
	
	/**
	 * M�ngija k�imis�igsuse tagastamine.
	 */
	public boolean getPermission() {
		return permission;
	}
	
	/**
	 * K�es olevate kaartide tagastamine
	 * @return k�es olevad kaardid
	 */
	public List<Card> getCards() {
		return hand;
	}
	/**
	 * Tapmiskaardi tagastamine
	 * @return tapetav kaart
	 */
	public Card getKillCard() {
		return kill;
	}
	/**
	 * Kaardi nime j�rgi otsimine
	 * @param string kaardi nimi
	 * @return kaart
	 */
	public Card getCardByName(String string) {
		for(Card a : hand) {
			if(string.equals(a.getName())) return a;
		}
		return null;
	}
	/**
	 * Teiste m�ngijate lisamine
	 * @param serverPlayers - teiste m�ngijate list
	 */
	public void addPlayers(List<String> serverPlayers) {
		for(String a : serverPlayers) {
			if(!a.equals(name))players.add(a);
		}
	}
	
	/**
	 * Unogames v�rdlemiseks, kui nimed on samad siis on v�rdsed
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
		System.out.println("Lauale k�idi "+kill.getColor() + " " + kill.getValue());
		if(killColor!=null)System.out.println("Pead k�ima kaardi, mille v�rv on: " + killColor);
		if(permission)System.out.println("------------------------SINU K�IK!------------------------");
		else System.out.println("------------------------VASTASE K�IK!------------------------");
	}
	//prindib k�easolevad kaardid
	public void kaartidePrint() {
		for (Card a : hand) {
			System.out.println(name + ": " + a.getColor() + " " + a.getValue());
		}
	}
	//tagastab teised m�ngijad
	public void teisedMangijad() {
		System.out.println(name +">>Teised m�ngijad on:" + players);
	}
}
