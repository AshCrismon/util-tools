package pers.ash.util.encryption;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class DESCoderTest {

	private String data = "使用对称加密算法DES";
	
	@Test
	public void testEncrypt() throws Exception{
		byte[] key = DESCoder.initKey();
		byte[] encryptedData = DESCoder.encrypt(data.getBytes(), key);
		byte[] decryptedData = DESCoder.decrypt(encryptedData, key);
		String outputData = new String(decryptedData);
		
		System.out.println("原始数据：" + data);
		System.out.println("密钥：" + Base64.encodeBase64String(key));
		System.out.println("加密后: " + Base64.encodeBase64String(encryptedData));
		System.out.println("解密后: " + outputData);
		assertEquals(data, outputData);
	}
	
	@Test
	public void testEncrypt2() throws Exception{
		byte[] key = DESCoder.initKey();
		String encryptedData = DESCoder.encryptBase64String(data.getBytes(), key);
		String decryptedData = DESCoder.decryptBase64String(encryptedData, key);
		
		String base64Data = Base64.encodeBase64String(data.getBytes());
		System.out.println("原始数据：" + base64Data);
		System.out.println("密钥：" + Base64.encodeBase64String(key));
		System.out.println("加密后: " + encryptedData);
		System.out.println("解密后: " + decryptedData);
		assertEquals(base64Data, decryptedData);
	}

}
