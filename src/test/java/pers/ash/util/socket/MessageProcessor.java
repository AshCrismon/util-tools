package pers.ash.util.socket;

import java.io.InputStream;
import java.io.OutputStream;

public interface MessageProcessor<T> {

	public void receiveMessage(InputStream inStream);
	
	public void sendMessage(T message, OutputStream outStream);
}
