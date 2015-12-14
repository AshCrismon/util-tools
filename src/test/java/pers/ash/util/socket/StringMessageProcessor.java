package pers.ash.util.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StringMessageProcessor implements MessageProcessor<String>{

//	private static final Logger logger = LoggerFactory.getLogger("StringMessageProcessor");
	
	private static final String END_FLAG = "@@@@";
	
	@Override
	public void receiveMessage(InputStream inStream) {
		byte[] buf = new byte[1024];
		try {
			//不要使用这个while((len = inStream.read(buf)) != -1)条件判断,因为IO是阻塞式的
			while(true){
				int len = inStream.read(buf);
				String data = new String(buf, 0, len);
				print("received data : " + data);
				if(data.endsWith(END_FLAG)){
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String message, OutputStream outStream) {

		try {
			print("send data : " + message);
			outStream.write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print(String data){
		System.out.println(data);
	}
	
}
