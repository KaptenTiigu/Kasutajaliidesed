package Message.Server;

import Server.ClientSession;
import Klient.Client;
import Message.Message;

public class RejectMessage implements Message {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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