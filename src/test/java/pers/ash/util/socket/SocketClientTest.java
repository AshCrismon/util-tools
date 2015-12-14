package pers.ash.util.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientTest {

	private static MessageProcessor<String> messageProcessor = new StringMessageProcessor();
	
	public static void main(String[] args) throws Exception{
		// 连接到服务器
		Socket socket = new Socket("127.0.0.1", 6666);

		// 发送数据
		OutputStream outStream = socket.getOutputStream();
		messageProcessor.sendMessage("a message from client @@@@", outStream); //@@@@表示结束符
		
		// 接收数据
		InputStream inStream = socket.getInputStream();
		messageProcessor.receiveMessage(inStream);
		
		socket.close();
	}
	
}
