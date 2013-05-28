package Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ActiveSessions {
	private Collection<ClientSession> sessionList 
			= new ArrayList<ClientSession>(); // M�ngijate kollektsioon

	public synchronized void addSession(ClientSession s) { 	// this lukku!
		sessionList.add(s);
		System.out.println("Saabus uus m�ngija: " + s);
	}

	public synchronized Iterator<ClientSession> iterator() { 			// kus s�nkronriseeritakse? 
		return sessionList.iterator();
	}
	
	 public int getNumberOfClients() {
		  return sessionList.size();
		 }
	 
	public synchronized ClientSession getNextClientSession(ClientSession session) {
		boolean next = false;
		//Kui session oli viimane, siis j�rgmine session on listis esimene.
		if (!sessionList.isEmpty()) {
			if (session == ((ArrayList<ClientSession>) sessionList).get(sessionList.size()-1)) {
				return ((ArrayList<ClientSession>) sessionList).get(0);
			}	
		}
		for (ClientSession listSession : sessionList) {
			if (listSession == session) {
				next = true;
			} else if (next) {
				return listSession;
			}			
		} 
		return session;
	}
}