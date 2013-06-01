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
	 * Kui server saab kliendi s�numi vastu
	 * @param s kliendi sessioon
	 */
	void onReceive(ClientSession s);
	/**
	 * Kui klient v�tab serveri s�numi
	 * 
	 * @param c - Client
	 */
	void onReceive(Client c);
	/**
	 * Meetod tagastab adressaadi.
	 * @return aadress, kellele s�nu saadetakse
	 */
	String getAdress();
}
