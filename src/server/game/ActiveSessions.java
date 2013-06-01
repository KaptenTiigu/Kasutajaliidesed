package server.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



/**
 * Siin hoitakse aktiivseid mängijad, kes võtavad osa mängust.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 *
 */
public class ActiveSessions {
	/**
	 * List kus hoitakse aktiivseid ClientSessione
	 */
	private Collection<ClientSession> sessionList 
			= new ArrayList<ClientSession>(); // Mängijate kollektsioon

	/**
	 * Uue CLientSessioni lisamine
	 * @param s - kliendi sessioon
	 */
	public synchronized void addSession(ClientSession s) { 	// this lukku!
		sessionList.add(s);
		System.out.println("Saabus uus mängija: " + s.getName());
	}

	/**
	 * Listi iteraatori tagastamine
	 * @return iteraator
	 */
	public synchronized Iterator<ClientSession> iterator() { 			// kus sünkronriseeritakse? 
		return sessionList.iterator();
	}
	
	/**
	 * Klientide arvu teadasaamine.
	 * @return SessionListi elementide arv
	 */
	public int getNumberOfClients() {
		  return sessionList.size();
		 }
	 
	/**
	 * Sissentavast ClientSessionist järgmise leidmine
	 * @param session - ClientSession
	 * @return järgmine ClientSession
	 */
	public synchronized ClientSession getNextClientSession(ClientSession session) {
		boolean next = false;
		//Kui session oli viimane, siis järgmine session on listis esimene.
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