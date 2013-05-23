package Message;

import java.util.ArrayList;

import Klient.Client;
import Server.ClientSession;

public class WelcomeMessage implements Message {
	private String playerName;
	//private ArrayList<String> players;
	
	public WelcomeMessage (String name){
		playerName = name;
		//this.players = players;
	}

	@Override
	public void onReceive(ClientSession s) {
	}

	@Override
	public String getAdress() {
		return playerName;
	}

	@Override
	public void onReceive(Client c) {
		// TODO Auto-generated method stub
		
	}
	
	public String getPlayerName() {
		return playerName;
	}
}
