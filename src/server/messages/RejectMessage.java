package server.messages;

import server.game.ClientSession;
import klient.Client;
import yhiskasutatavad.Message;

/**
 * S�num saadetakse kui m�ngijate limiit on �letatud.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 */
public class RejectMessage implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * M�ngija nimi, kellele s�num edastatakse
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
	  System.out.println("M�ngijate limiit on �letatud. ");
	  c.setStopThread();
	 }
	
	 @Override
	 public String getAdress() {
	  return address;
	 }

}