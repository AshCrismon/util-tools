package pers.ash.util.smack;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class SmackConnectionFactory extends Configuration {

	public XMPPTCPConnection getConnection() {
		XMPPTCPConnection conn = new XMPPTCPConnection(configBuilder.build());
		return conn;
	}

	public XMPPTCPConnection getConnection(String serviceName, String serverName, int port) {
		validate(serviceName, serverName, port);
		synchronized (configBuilder) {
			configBuilder.setServiceName(serviceName);
			configBuilder.setHost(serverName);
			configBuilder.setPort(port);
		}

		XMPPTCPConnection conn = new XMPPTCPConnection(configBuilder.build());
		return conn;
	}

	public void validate(String serviceName, String serverName, int port) {
		if (StringUtils.isBlank(serviceName) || StringUtils.isBlank(serverName)
				|| port < 0 || port > 65535) {
			throw new IllegalArgumentException("invalid argument serviceName or serverName or port");
		}
	}

}
