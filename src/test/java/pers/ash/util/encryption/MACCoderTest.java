package pers.ash.util.encryption;

import static org.junit.Assert.*;

import org.junit.Test;

public class MACCoderTest {

	private String data = "使用带密钥的消息摘要算法MAC";
	
	@Test
	public void testEncodeHmacMD5() throws Exception {
		byte[] key = MACCoder.initHmacMD5Key();
		String data1 = MACCoder.encodeHmacMD5HexString(data.getBytes(), key);
		String data2 = MACCoder.encodeHmacMD5HexString(data.getBytes(), key);
		System.out.println("HmacMd5算法处理后：" + data1);
		assertEquals(data1, data2);
	}

	@Test
	public void testEncodeHmacSHA1() throws Exception {
		byte[] key = MACCoder.initHmacSHAKey();
		String data1 = MACCoder.encodeHmacSHA1HexString(data.getBytes(), key);
		String data2 = MACCoder.encodeHmacSHA1HexString(data.getBytes(), key);
		System.out.println("HmacSHA1算法处理后：" + data1);
		assertEquals(data1, data2);
	}

	@Test
	public void testEncodeHmacSHA256() throws Exception {
		byte[] key = MACCoder.initHmacSHA256();
		String data1 = MACCoder.encodeHmacSHA256HexString(data.getBytes(), key);
		String data2 = MACCoder.encodeHmacSHA256HexString(data.getBytes(), key);
		System.out.println("HmacSHA256算法处理后：" + data1);
		assertEquals(data1, data2);
	}

	@Test
	public void testEncodeHmacSHA384() throws Exception {
		byte[] key = MACCoder.initHmacSHA384();
		String data1 = MACCoder.encodeHmacSHA384HexString(data.getBytes(), key);
		String data2 = MACCoder.encodeHmacSHA384HexString(data.getBytes(), key);
		System.out.println("HmacSHA384算法处理后：" + data1);
		assertEquals(data1, data2);
	}

	@Test
	public void testEncodeHmacSHA512() throws Exception {
		byte[] key = MACCoder.initHmacSHA512();
		String data1 = MACCoder.encodeHmacSHA512HexString(data.getBytes(), key);
		String data2 = MACCoder.encodeHmacSHA512HexString(data.getBytes(), key);
		System.out.println("HmacSHA512算法处理后：" + data1);
		assertEquals(data1, data2);
	}

}
