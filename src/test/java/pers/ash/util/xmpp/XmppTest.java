package pers.ash.util.xmpp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.sasl.SASLAnonymous;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.junit.Before;
import org.junit.Test;

/**
 * smack-4.1.5 junit test
 * 
 * @author Asher
 * 
 */
public class XmppTest {

	private XMPPTCPConnectionConfiguration config = null;
	
	@Before
	public void initConfig(){
		//自定义配置
		config = XMPPTCPConnectionConfiguration
				.builder()//
				.setServiceName("it-pc")//这里是主机名的小写，以openfire控制台看到的Server Name为主
				.setHost("localhost")//
				.setPort(5222)//客户端连接服务器的标准端口
				.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)//禁用ssl
				.setDebuggerEnabled(true)//
				.setSendPresence(true)//
				.build();
	}
	
	@Test
	public void testDefaultXmppTcpConnection() throws Exception{
		//默认使用org.jivesoftware.smack文件夹下面的smack-config配置文件,默认会启用ssl
		AbstractXMPPConnection defaultConn = new XMPPTCPConnection("admin", "admin", "it-pc");
		defaultConn.connect();
		assertTrue(defaultConn.isConnected());
		assertTrue(defaultConn.isAuthenticated());
	}
	
	@Test
	public void testCustomizedXmppTcpConnection() throws Exception {

		AbstractXMPPConnection customizedConn = new XMPPTCPConnection(config);
		System.out.println("连接中...");
		customizedConn.connect();
		assertTrue(customizedConn.isConnected());
		System.out.println("连接成功...");
		
		System.out.println("认证中...");
		customizedConn.login("admin", "admin");
		assertTrue(customizedConn.isAuthenticated());
		System.out.println("认证通过...");
		
		System.out.println("断开连接...");
		customizedConn.disconnect();
		assertFalse(customizedConn.isConnected());
		System.out.println("成功断开连接...");
	}
	
	/**
	 * 向xmpp服务器注册用户
	 * @throws Exception
	 */
	@Test
	public void testXmppRegist() throws Exception{
		AbstractXMPPConnection conn = new XMPPTCPConnection(config);
		conn.connect();
		AccountManager accountManager = AccountManager.getInstance(conn);
		accountManager.createAccount("asher", "asher");
		
		conn.login("asher", "asher");
		assertTrue(conn.isAuthenticated());
		conn.disconnect();
	}
	
	@Test
	public void testXmppLogin() throws Exception{
		AbstractXMPPConnection conn = new XMPPTCPConnection(config);
		conn.connect();
		conn.login("asher", "asher");
		assertTrue(conn.isAuthenticated());
		conn.disconnect();
	}
	
	/**
	 * 测试通信，admin给自己发消息
	 * @throws Exception
	 */
	@Test
	public void testXmppChat() throws Exception{
		AbstractXMPPConnection conn = new XMPPTCPConnection(config);
		conn.connect();
		conn.login("admin", "admin");
		ChatManager chatManager = ChatManager.getInstanceFor(conn);
		Chat chat = chatManager.createChat("admin@it-pc/Smack", new ChatMessageListener(){

			@Override
			public void processMessage(Chat chat, Message message) {
				System.out.println("user " + chat.getParticipant() + " received message : " + message.getBody());
			}
			
		});

		Message message = new Message();
		message.setBody("hello");
		chat.sendMessage(message);
	}
	
	/**
	 * 测试通信，admin向asher发消息
	 * @throws Exception
	 */
	@Test
	public void testXmppChat2() throws Exception{
		AbstractXMPPConnection conn1 = new XMPPTCPConnection(config);
		conn1.connect();
		conn1.login("admin", "admin");
		
		ChatManager chatManager1 = ChatManager.getInstanceFor(conn1);
		Chat chat1 = chatManager1.createChat("asher@it-pc/Smack", new ChatMessageListener(){

			@Override
			public void processMessage(Chat chat, Message message) {
				System.out.println("user " + chat.getParticipant() + " received a xmpp message : " + message.getBody());
			}
			
		});
		
		Message message = new Message();
		message.setBody("hello");
		message.setFrom(conn1.getUser());
		System.out.println("user " + conn1.getUser() + " is sending a xmpp message : " + message.getBody());
		chat1.sendMessage(message);
		
		///////////////////////////////////////////////////////////////
		AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
		conn2.connect();
		conn2.login("asher", "asher");
		
		ChatManager chatManager2 = ChatManager.getInstanceFor(conn2);
		chatManager2.addChatListener(new ChatManagerListener(){

			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				if(!createdLocally){
					chat.addMessageListener(new ChatMessageListener(){

						@Override
						public void processMessage(Chat chat, Message message) {
							System.out.println("user " + chat.getParticipant() + " received a xmpp message : " + message.getBody());
						}
						
					});
				}
				
			}
			
		});

	}
	
	
	@Test
	public void test() throws Exception{
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration
				.builder()//
				.setServiceName("it-pc")//这里是主机名的小写，以openfire控制台看到的Server Name为主
				.setHost("localhost")//
				.setPort(5222)//客户端连接服务器的标准端口
				.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)//禁用ssl
				.setDebuggerEnabled(true)//
				.build();
		AbstractXMPPConnection conn = new XMPPTCPConnection(config);
		conn.connect();
		Presence presence = new Presence(Presence.Type.unavailable);
		
		presence.setStatus("Gone fishing");
		conn.sendStanza(presence);
	}
	
}
