package pers.ash.util.encryption;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 经典对称加密算法：DES<br>
 * DES算法已经不再安全<br>
 * 数据+密钥+加密算法=密文<br>
 * 密文+密钥+解密算法=明文<br>
 * 依赖commons-codec
 * @author Ash Crismon
 */
public abstract class DESCoder {
	
	/**
	 * 经典对称加密算法: DES<br>
	 * Java7只支持56位密钥 <br>
	 */
	public static final String KEY_ALGORITHM = "DES";
	
	/**
	 * 加密算法/工作模式/填充方式
	 */
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	
	public static final int KEY_SIZE = 56;
	
	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return Key 密钥对象
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception{
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * 解密
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @param data 待解密数据
	 * @param base64Key base64编码的密钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String base64Key) throws Exception{
		return decrypt(data, Base64.decodeBase64(base64Key));
	}
	
	
	/**
	 * 解密
	 * @param base64Data base64编码的待解密数据
	 * @param key 密钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decrypt(String base64Data, byte[] key) throws Exception{
		return decrypt(Base64.decodeBase64(base64Data), key);
	}
	
	
	/**
	 * 解密
	 * @param base64Data base64编码的待解密数据
	 * @param base64Key base64编码的密钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static byte[] decrypt(String base64Data, String base64Key) throws Exception{
		return decrypt(Base64.decodeBase64(base64Data), Base64.decodeBase64(base64Key));
	}
	
	/**
	 * 解密
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return String 解密后的数据用base64编码
	 * @throws Exception
	 */
	public static String decryptBase64String(byte[] data, byte[] key) throws Exception{
		return Base64.encodeBase64String(decrypt(data, key));
	}
	
	/**
	 * 解密
	 * @param data 待解密数据
	 * @param base64Key base64编码的密钥
	 * @return String 解密后的数据用base64编码
	 * @throws Exception
	 */
	public static String decryptBase64String(byte[] data, String base64Key) throws Exception{
		return Base64.encodeBase64String(decrypt(data, base64Key));
	}
	
	/**
	 * 解密
	 * @param base64Data base64编码的待解密数据
	 * @param key 密钥
	 * @return String 解密后的数据用base64编码
	 * @throws Exception
	 */
	public static String decryptBase64String(String base64Data, byte[] key) throws Exception{
		return Base64.encodeBase64String(decrypt(base64Data, key));
	}
	
	/**
	 * 解密
	 * @param base64Data base64编码的待解密数据
	 * @param base64Key base64编码的密钥
	 * @return String 解密后的数据用base64编码
	 * @throws Exception
	 */
	public static String decryptBase64String(String base64Data, String base64Key) throws Exception{
		return Base64.encodeBase64String(decrypt(base64Data, base64Key));
	}
	
	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String base64Key) throws Exception{
		return encrypt(data, Base64.decodeBase64(base64Key));
	}
	
	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return String 加密后的数据用base64编码
	 * @throws Exception
	 */
	public static String encryptBase64String(byte[] data, byte[] key) throws Exception{
		return Base64.encodeBase64String(encrypt(data, key));
	}
	
	
	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * @throws Exception
	 */
	public static String encryptBase64String(byte[] data, String base64Key) throws Exception{
		return Base64.encodeBase64String(encrypt(data, base64Key));
	}
	
	/**
	 * 生成密钥
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception{
		KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
		keyGen.init(KEY_SIZE);
		SecretKey secretKey = keyGen.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 将16进制字符串转换成字节数组
	 * @param key 16进制字符串
	 * @return byte[] 相同值的byte数组
	 * @throws Exception
	 */
	public static byte[] decodeHex(String hexKey) throws Exception{
		return Hex.decodeHex(hexKey.toCharArray());
	}

}
