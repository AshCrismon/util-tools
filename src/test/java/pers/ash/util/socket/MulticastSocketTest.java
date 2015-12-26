package pers.ash.util.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.junit.Test;

public class MulticastSocketTest {

	/**
	 * 多播地址：224.0.0.0 - 239.255.255.255
	 * 	局部链接多播地址：224.0.0.0 - 224.0.0.255 	为路由协议和其它用途保留地址，路由器不转发此范围的IP包
	 * 	预留多播地址：	224.0.1.0 - 238.255.255.255	可用于全球范围或网络协议
	 * 	管理权限多播地址：239.0.0.0 - 239.255.255.255	可供组织内部使用，不可用于Internet
	 */
	@Test
	public void multicastSocketTest() throws Exception{
		Thread receiver = new Thread(new Receiver());
		Thread sender = new Thread(new Sender());
		receiver.start();
		sender.start();
		receiver.join();
		sender.join();
	}

	public class Receiver extends Configure implements Runnable{
		
		private MulticastSocket socket = null;
		private InetAddress group = null;
		private String host = "230.0.0.1";
		private volatile boolean stop = false;
		
		public Receiver(){
			try {
				socket = new MulticastSocket(port);
				group = InetAddress.getByName(host);
				socket.joinGroup(group);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			byte[] buf = new byte[1024];
			while(!stop){
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					socket.receive(dp);
					byte[] data = dp.getData();
					System.out.println("received data : " + new String(data));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stop(){
			this.stop = true;
			if(socket != null){
				socket.close();
			}
		}
	}
	
	public class Sender extends Configure implements Runnable{
		
		private MulticastSocket socket = null;
		private InetAddress group = null;
		private String host = "230.0.0.1";
		
		public Sender(){
			try {
				socket = new MulticastSocket();
				group = InetAddress.getByName(host);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			byte[] buf = "hello".getBytes();
			DatagramPacket dp = new DatagramPacket(buf, buf.length, group, port);
			try {
				socket.send(dp);
				System.out.println("send data : " + new String(buf));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class Configure{
		protected int port = 6666;
	}
}
