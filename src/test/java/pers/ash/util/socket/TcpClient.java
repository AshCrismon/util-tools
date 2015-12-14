package pers.ash.util.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient extends Configurator implements Runnable{

	private Socket socket = null;
	private volatile boolean stop = false;
	private MessageProcessor<String> msgProcessor;
	private InputStream inStream = null;
	private OutputStream outStream = null;

	public TcpClient(){
		this(null);
	}
	
	public TcpClient(MessageProcessor<String> msgProcessor){
		super();
		this.msgProcessor = msgProcessor == null ? new StringMessageProcessor() : msgProcessor;
		try {
			socket = new Socket(serverIp, serverPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (!stop) {
			try {
				if(inStream == null){
					inStream = socket.getInputStream();
				}
				msgProcessor.receiveMessage(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMsg(String message) throws IOException{
		if(outStream == null && socket != null && socket.isConnected()){
			outStream = socket.getOutputStream();
		}
		msgProcessor.sendMessage(message, outStream);
	}
	
	public void stop(){
		this.stop = true;
		try {
			if (inStream != null) {
				inStream.close();
			}
			if(outStream != null){
				outStream.close();
			}
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
