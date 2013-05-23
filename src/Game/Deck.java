package Game;

import java.util.Collections;

public class Deck extends Pile{

	public void shuffle() {
		Collections.shuffle(cards);
	}

}
