package pers.ash.util.encryption;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class AESCoderTest {

	private String data = "使用对称加密算法AES";
	
	@Test
	public void testEncrypt() throws Exception {
		byte[] key = AESCoder.initKey();
		byte[] encryptedData = AESCoder.encrypt(data.getBytes(), key);
		byte[] decryptedData = AESCoder.decrypt(encryptedData, key);
		String outputData = new String(decryptedData);
		
		System.out.println("原始数据：" + data);
		System.out.println("密钥：" + Base64.encodeBase64String(key));
		System.out.println("加密后: " + Base64.encodeBase64String(encryptedData));
		System.out.println("解密后: " + outputData);
		assertEquals(data, outputData);
	}
	
	@Test
	public void testEncrypt2() throws Exception {
		byte[] key = AESCoder.initKey();
		String encryptedData = AESCoder.encryptBase64String(data.getBytes(), key);
		String decryptedData = AESCoder.decryptBase64String(encryptedData, key);
		
		String base64Data = Base64.encodeBase64String(data.getBytes());
		System.out.println("原始数据：" + base64Data);
		System.out.println("密钥：" + Base64.encodeBase64String(key));
		System.out.println("加密后: " + encryptedData);
		System.out.println("解密后: " + decryptedData);
		assertEquals(base64Data, decryptedData);
	}

}
