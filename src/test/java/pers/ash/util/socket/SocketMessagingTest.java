package pers.ash.util.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SocketMessagingTest {

	@Test
	public void testSocketMessaging() throws Exception{
		ExecutorService pool = Executors.newCachedThreadPool();
		Server server = new Server();
		TcpClient client = new TcpClient();
		pool.execute(server);
		pool.execute(client);
		for(int i = 0; i < 10; i++){
			client.sendMsg("send message " + i);
		}
		client.sendMsg("@@@@");
		pool.shutdown();
		pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS); //放在shutdown后，主线程等待所有子线程结束
	}
	
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		TcpClient client = new TcpClient();
		
		new Thread(server).start();
		new Thread(client).start();
		
		for(int i = 0; i < 10; i++){
			client.sendMsg("send message " + i);
		}
		client.sendMsg("@@@@");
		client.stop();
	}
	
}
