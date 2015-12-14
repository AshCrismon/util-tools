package pers.ash.util.smack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.iqregister.AccountManager;

public class SubjectManager {

	private SmackConnectionFactory connFactory = new SmackConnectionFactory();
	private Hashtable<String, XMPPTCPConnection> connTable = new Hashtable<String, XMPPTCPConnection>(50);
	
	public synchronized boolean login(String username, String password) {
		XMPPTCPConnection conn = connFactory.getConnection();
		boolean isLogined = false;
		try {
			conn.connect();
			conn.login(username, password);
			isLogined = true;
		} catch (SmackException | IOException | XMPPException e) {
			e.printStackTrace();
			if (conn.isConnected()) {
				conn.disconnect();
			}
			isLogined = false;
		}
		if (isLogined) {
			connTable.put(username, conn);
		}
		return isLogined;
	}
	
	public synchronized void logout(String username){
		if(isConnected(username)){
			XMPPTCPConnection conn = connTable.get(username);
			conn.instantShutdown();
			connTable.remove(username);
		}
	}
	
	public synchronized boolean isConnected(String username){
		XMPPTCPConnection conn = connTable.get(username);
		return conn.isConnected();
	}
	
	public boolean isLogined(String username){
		XMPPTCPConnection conn = connTable.get(username);
		return conn.isAuthenticated();
	}
	
	public boolean regist(String username, String password){
		return regist(username, password, new HashMap<String, String>());
	}
	
	public synchronized boolean regist(String username, String password, Map<String, String> attributes){
		boolean success = false;
		if(isConnected(username)){
			XMPPTCPConnection conn = connTable.get(username);
			try {
				AccountManager.getInstance(conn).createAccount(username, password, attributes);
				success = true;
			} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}
	
	public synchronized boolean changePassword(String username, String newPassword){
		boolean success = false;
		if(isLogined(username)){
			XMPPTCPConnection conn = connTable.get(username);
			try {
				AccountManager.getInstance(conn).changePassword(newPassword);
				success = true;
			} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}
	
	public synchronized boolean deleteAccount(String username){
		boolean success = false;
		if(isLogined(username)){
			XMPPTCPConnection conn = connTable.get(username);
			try {
				AccountManager.getInstance(conn).deleteAccount();
				success = true;
			} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}
}
