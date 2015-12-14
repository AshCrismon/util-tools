package pers.ash.util.smack;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public abstract class Configuration {
	
	protected XMPPTCPConnectionConfiguration.Builder configBuilder = null;
	
	public Configuration(){
		configBuilder = XMPPTCPConnectionConfiguration
				.builder()//
				.setServiceName("it-pc")//这里是主机名的小写，以openfire控制台看到的Server Name为主
				.setHost("localhost")//
				.setPort(5222)//客户端连接服务器的标准端口
				.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)//禁用ssl
				.setDebuggerEnabled(true)//
				.setSendPresence(true);
	}
}
