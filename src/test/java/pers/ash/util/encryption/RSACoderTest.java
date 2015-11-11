package pers.ash.util.encryption;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

public class RSACoderTest {

	private String data = "使用非对称算法RSA";
	
	@Before
	public void init(){
		System.out.println("原始数据：" + data);
	}
	
	@Test
	public void testInitKeyPair() throws Exception {
		Map<String, Object> keyMap = RSACoder.initKeyPair();
		String publicKey = RSACoder.getPublicKeyHexString(keyMap);
		String privateKey = RSACoder.getPrivateKeyHexString(keyMap);
		System.out.println("public key : " + publicKey);
		System.out.println("private key : " + privateKey);
	}
	
	@Test
	public void testEncryptByPublicKey() throws Exception {
		//1.初始化密钥对
		Map<String, Object> keyMap = RSACoder.initKeyPair();
		
		//2.公钥加密
		byte[] publicKey = RSACoder.getPublicKey(keyMap);
		byte[] encryptedData = RSACoder.encryptByPublicKey(data.getBytes(), publicKey);
		System.out.println("公钥加密后的数据：");
		print(encryptedData);
		
		//3.私钥解密
		byte[] privateKey = RSACoder.getPrivateKey(keyMap);
		byte[] decryptedData = RSACoder.decryptByPrivateKey(encryptedData, privateKey);
		System.out.println("私钥解密后的数据：" + new String(decryptedData));
		
		assertArrayEquals(data.getBytes(), decryptedData);
	}

	@Test
	public void testEncryptByPrivateKey() throws Exception{
		// 1.初始化密钥对
		Map<String, Object> keyMap = RSACoder.initKeyPair();

		// 2.私钥加密
		byte[] privateKey = RSACoder.getPrivateKey(keyMap);
		byte[] encryptedData = RSACoder.encryptByPrivateKey(data.getBytes(), privateKey);
		System.out.println("私钥加密后的数据：");
		print(encryptedData);
		
		// 3.公钥解密
		byte[] publicKey = RSACoder.getPublicKey(keyMap);
		byte[] decryptedData = RSACoder.decryptByPublicKey(encryptedData, publicKey);
		System.out.println("公钥解密后的数据：" + new String(decryptedData));
		
		assertArrayEquals(data.getBytes(), decryptedData);
	}
	
	public void print(byte[] data){
		System.out.println(Base64.encodeBase64String(data));
	}

	@Test
	public void test() throws Exception{
	
	}
}
