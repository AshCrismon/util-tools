package pers.ash.util.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class Server extends Configurator implements Runnable{

	private ServerSocket serverSocket = null;
	private MessageProcessor<String> msgProcessor;
	private volatile boolean stop = false;
	
	public Server(){
		this(null);
	}
	
	public Server(MessageProcessor<String> msgProcessor){
		this.msgProcessor = msgProcessor == null ? new StringMessageProcessor() : msgProcessor;
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(!stop){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				new Thread(new Chat(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void stop(){
		this.stop = true;
	}
	
	class Chat implements Runnable{
		private InputStream inStream = null;
		private OutputStream outStream = null;
		private volatile boolean stop = false;
		private Socket socket = null;
		
		public Chat(Socket socket) throws IOException{
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
			this.socket = socket;
		}

		@Override
		public void run() {
			while(!stop){
				msgProcessor.receiveMessage(inStream);
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}

