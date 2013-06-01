package yhiskasutatavad;

import java.io.Serializable;
import java.util.List;

import server.game.ClientSession;

import klient.Client;

/**
 * Serveri ja kliendi vahelisel suhtlusel kasutatavad objektid.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public interface Message extends Serializable {
	/**
	 * Kui server saab kliendi sõnumi vastu
	 * @param s kliendi sessioon
	 */
	void onReceive(ClientSession s);
	/**
	 * Kui klient võtab serveri sõnumi
	 * 
	 * @param c - Client
	 */
	void onReceive(Client c);
	/**
	 * Meetod tagastab adressaadi.
	 * @return aadress, kellele sõnu saadetakse
	 */
	String getAdress();
}
