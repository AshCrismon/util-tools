package pers.ash.util.encryption;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class SHACoderTest {

	private String data = "使用SHA做消息摘要";
	
	@Test
	public void testEncodeSHA() throws Exception {
		byte[] b1 = SHACoder.encodeSHA(data.getBytes());
		byte[] b2 = SHACoder.encodeSHA(data.getBytes());
		System.out.println("SHA处理后：" + Hex.encodeHexString(b1));
		assertArrayEquals(b1, b2);
	}

	@Test
	public void testEncodeSHA256() throws Exception {
		byte[] b1 = SHACoder.encodeSHA256(data.getBytes());
		byte[] b2 = SHACoder.encodeSHA256(data.getBytes());
		System.out.println("SHA-256处理后：" + Hex.encodeHexString(b1));
		assertArrayEquals(b1, b2);
	}

	@Test
	public void testEncodeSHA384() throws Exception {
		byte[] b1 = SHACoder.encodeSHA384(data.getBytes());
		byte[] b2 = SHACoder.encodeSHA384(data.getBytes());
		System.out.println("SHA-384处理后：" + Hex.encodeHexString(b1));
		assertArrayEquals(b1, b2);
	}

	@Test
	public void testEncodeSHA512() throws Exception {
		byte[] b1 = SHACoder.encodeSHA512(data.getBytes());
		byte[] b2 = SHACoder.encodeSHA512(data.getBytes());
		System.out.println("SHA-512处理后：" + Hex.encodeHexString(b1));
		assertArrayEquals(b1, b2);
	}

}
