package Game;

import java.io.Serializable;

public abstract class Card implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Kaardi v‰rvilised v‰‰rtused
	 * @author LehoRaiguma
	 *
	 */
	public enum Color {
		BLUE, RED, GREEN, YELLOW;
	}
	/**
	 * Kaardi nimelised v‰‰rtused
	 * @author LehoRaiguma
	 *
	 */
	public enum Value {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, DRAWONE, DRAWTWO,
		WILD, WILDDRAWFOUR;		
	}
	
	private Color color;
	private Value value;
	
	/**
	 * Kaardile v‰rvi ja v‰‰rtuse andmine
	 * @param col - v‰rv
	 * @param val - v‰‰rtus
	 */
	public Card(Color col, Value val) {
		this.color = col;
		this.value = val;
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
	 * Kaartide vırdlemine. Kas etteantud kaarti vıib k‰ia lauale.
	 * @param card - kaart
	 * @param col - v‰rvus
	 * @return true - kui kaarti tohib k‰ia, false - kui kaarti ei tohi k‰ia
	 */
	public boolean compareCards(Card card, Color col) {
		if (col == null) {
			if (value.compareTo(card.getValue()) == 0 || color.compareTo(card.getColor()) == 0) {
				return true;
			}
			return false;
		} else {
			if (color.compareTo(col) == 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Meetod, mis aktiveerib erisugused tegevused, mis antud kaardiga seotud on.
	 */
	public void action() {}
}