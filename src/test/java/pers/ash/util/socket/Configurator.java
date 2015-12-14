package pers.ash.util.socket;

import java.util.Properties;

import pers.ash.util.properties.PropertyUtils;

public class Configurator {
	
	private String configFileName = "socket.properties";
	private final static String SERVER_IP = "server.ip";
	private final static String SERVER_PORT = "server.port";
	protected String serverIp;
	protected int serverPort;
	
	public Configurator(){
		Properties props = PropertyUtils.getProperties(configFileName);
		serverIp = props.getProperty(SERVER_IP);
		serverPort = Integer.valueOf(props.getProperty(SERVER_PORT));
	}
}
