package yhiskasutatavad;

import java.io.Serializable;

import server.game.ClientSession;



/**
 * Klassis on kaartide loogika.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public abstract class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Kaardi v‰rvilised v‰‰rtused
	 */
	public enum Color {
		BLUE, RED, GREEN, YELLOW, NONE;
	}
	/**
	 * Kaardi nimelised v‰‰rtused
	 */
	public enum Value {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, DRAWONE, DRAWTWO,
		WILD, WILDDRAWFOUR;		
	}
	
	private Color color;
	private Value value;
	private String name;
	/**
	 * Kaardile v‰rvi ja v‰‰rtuse andmine
	 * @param col - v‰rv
	 * @param val - v‰‰rtus
	 */
	public Card(Color col, Value val) {
		this.color = col;
		this.value = val;
		if(col == Card.Color.NONE) name = value.toString();
		else name = col + " " + value;
	}
	
	/**
	 * Kaardi v‰rvuse tagastamine
	 * @return Color - kaardi v‰rvus
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Kaardi v‰‰rtuse tagastamine.
	 * @return Value - kaardi v‰‰rtus
	 */
	public Value getValue(){
		return value;
	}
	/**
	 * Kaardi nime tagastamine
	 * @return nimi
	 */
	public String getName(){
		return name;
	}
	 /**
	  * Kaartide vırdlemine. Kas etteantud kaarti vıib k‰ia lauale.
	  * @param card - kaart
	  * @param killColor - tapetava kaardi v‰rv
	  * @return true - kui kaarti tohib k‰ia, false - kui kaarti ei tohi k‰ia
	  */
	 public boolean compareCards(Card card, Color killColor) {
	  if(color.compareTo(Color.NONE) == 0) {
	   return true;
	  }
	  if (killColor.compareTo(Color.NONE) == 0) {
	   if (value.compareTo(card.getValue()) == 0 || color.compareTo(card.getColor()) == 0) {
	    return true;
	   }
	   return false;
	  } else {
	   if (color.compareTo(killColor) == 0) {
	    return true;
	   }
	  }
	  return false;
	 }
	/**
	 * Meetod, mis aktiveerib erisugused tegevused serveris, mis antud kaardiga seotud on.
	 */
	public abstract void action(ClientSession sess);
	/**
	 * Klassis m‰‰ratakse, kas antud kaardi puhul on kasutajal vajalik ka v‰rvi valida.
	 * @return boolean
	 */
	public abstract boolean chooseColor();
}