package pers.ash.util.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerTest {

	private static MessageProcessor<String> messageProcessor = new StringMessageProcessor();
	
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(6666);
		Socket socket = serverSocket.accept();

		//接收数据
		InputStream inStream = socket.getInputStream();
		messageProcessor.receiveMessage(inStream); //server会阻塞，因为现在没有数据不能判断结束，除非服务器断开连接
		
		//发送数据
		OutputStream outStream = socket.getOutputStream();
		messageProcessor.sendMessage("a message from server @@@@", outStream); //@@@@表示结束符
		
		socket.close(); //socket关闭时会关闭inStream
//		serverSocket.close(); 服务端不关闭
	}
}
