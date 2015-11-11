package pers.ash.util.encryption;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 经典非对称加密算法 : RSA<br>
 * 依赖commons-codec
 * @author Ash Crismon
 */
public abstract class RSACoder {

	public static final String KEY_ALGORITHM = "RSA"; //非对称加密算法：rsa
	
	private static final String PUBLIC_KEY = "RSAPublicKey";//公钥
	
	private static final String PRIVATE_KEY = "RSAPrivateKey";//私钥
	
	/**
	 * RSA密钥长度
	 * 默认1024位
	 * 长度必须是64的倍数
	 * 范围在512~65536之间
	 */
	private static final int KEY_SIZE = 512;
	
	/**
	 * 私钥解密
	 * @param data 要解密的数据
	 * @param key 解密的私钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception{
		//1.获取私钥规范对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		//2.根据私钥规范生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		//3.解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥解密
	 * @param data 要解密的数据
	 * @param key 包含十六进制字符串的私钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String base64Key) throws Exception{
		return decryptByPrivateKey(data, Base64.decodeBase64(base64Key));
	}
	
	/**
	 * 公钥解密
	 * @param data 要解密的数据
	 * @param key 解密的公钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception{
		//1.获取公钥规范对象
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		//2.根据公钥规范生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		
		//3.解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * @param data 要解密的数据
	 * @param key 包含十六进制字符串的公钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String base64Key) throws Exception{
		return decryptByPublicKey(data, Base64.decodeBase64(base64Key));
	}
	
	/**
	 * 公钥加密
	 * @param data 要加密的数据
	 * @param key 公钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key)
			throws Exception {
		// 1.获取公钥规范对象
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		// 2.根据公钥规范生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		
		// 3.解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥加密
	 * @param data 要加密的数据
	 * @param base64Key base64编码的公钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String base64Key)
			throws Exception {
		return encryptByPublicKey(data, Base64.decodeBase64(base64Key));
	}
	
	
	/**
	 * 私钥加密
	 * @param data 要加密的数据
	 * @param key 私钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key)
			throws Exception {
		// 1.获取私钥规范对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 2.根据私钥规范生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 3.解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥加密
	 * @param data 要加密的数据
	 * @param base64Key base64编码的公钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String base64Key)
			throws Exception {
		return encryptByPrivateKey(data, Base64.decodeBase64(base64Key));
	}
	
	/**
	 * 初始化密钥对
	 * @return Map 密钥对
	 * @throws Exception
	 */
	public static Map<String, Object> initKeyPair() throws Exception{
		//1.实例化密钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		//2.初始化密钥对生成器
		keyPairGen.initialize(KEY_SIZE);
		//3.生成密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * 从密钥Map中获取公钥
	 * @param keyMap 密钥Map
	 * @return byte[] 公钥
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 从密钥Map中获取公钥
	 * @param keyMap 密钥Map
	 * @return String 16进制编码公钥
	 */
	public static String getPublicKeyHexString(Map<String, Object> keyMap){
		return Hex.encodeHexString(getPublicKey(keyMap));
	}
	
	/**
	 * 从密钥Map中获取私钥
	 * @param keyMap 密钥Map
	 * @return byte[] 私钥
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 从密钥Map中获取私钥
	 * @param keyMap 密钥Map
	 * @return String 16进制编码私钥
	 */
	public static String getPrivateKeyHexString(Map<String, Object> keyMap){
		return Hex.encodeHexString(getPrivateKey(keyMap));
	}
	
	/**
	 * 将16进制字符串转换成字节数组
	 * @param key 16进制字符串
	 * @return byte[] 相同值的byte数组
	 * @throws Exception
	 */
	public static byte[] getKey(String key) throws Exception{
		return Hex.decodeHex(key.toCharArray());
	}
}
