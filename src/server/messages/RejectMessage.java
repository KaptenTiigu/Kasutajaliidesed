package server.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Message;

/**
 * Sõnum saadetakse kui mängijate limiit on ületatud.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class RejectMessage implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Mängija nimi, kellele sõnum edastatakse
	 */
	private String address;
	 
	 public RejectMessage(String playerName) {
	  address = playerName;
	 }
	
	 @Override
	 public void onReceive(ClientSession s) {
	  
	 }
	
	 @Override
	 public void onReceive(Client c) {
	  System.out.println("Mängijate limiit on ületatud. ");
	  c.setStopThread();
	 }
	
	 @Override
	 public String getAdress() {
	  return address;
	 }

}