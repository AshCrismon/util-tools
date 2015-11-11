package pers.ash.util.encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 带有密钥的消息摘要算法MAC<br>
 * 依赖于commons-codec
 * @author Ash Crismon
 *
 */
public abstract class MACCoder {
	
	/**
	 * 根据指定算法初始化密钥
	 * @param keyGenType 密钥生成器算法类型
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] initKey(KeyGenType keyGenType) throws Exception{
		KeyGenerator keyGen = KeyGenerator.getInstance(keyGenType.getValue());
		SecretKey secretKey = keyGen.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 初始化HmacMD5密钥
	 * @return byte[] HmacMD5密钥
	 * @throws Exception
	 */
	public static byte[] initHmacMD5Key() throws Exception{
		return initKey(KeyGenType.HMACMD5);
	}
	
	/**
	 * HmacMD5消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacMD5密钥
	 * @return byte[] HmacMD5消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeHmacMD5(byte[] data, byte[] key)throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, KeyGenType.HMACMD5.getValue());
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * HmacMD5消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacMD5密钥
	 * @return String 16进制HmacMD5消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacMD5HexString(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacMD5(data, key));
	}
	
	/**
	 * HmacMD5消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacMD5密钥
	 * @return String base64编码的HmacMD5消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacMD5Base64String(byte[] data, byte[] key)throws Exception{
		return Base64.encodeBase64String(encodeHmacMD5(data, key));
	}
	
	/**
	 * 初始化HmacSHA1密钥
	 * @return byte[] HmacSHA1密钥
	 * @throws Exception
	 */
	public static byte[] initHmacSHAKey() throws Exception{
		return initKey(KeyGenType.HMACSHA1);
	}
	
	/**
	 * HmacSHA1消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA1密钥
	 * @return byte[] HmacSHA1消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeHmacSHA1(byte[] data, byte[] key)throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, KeyGenType.HMACSHA1.getValue());
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * HmacSHA1消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA1密钥
	 * @return String 16进制HmacSHA1消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA1HexString(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacSHA1(data, key));
	}
	
	/**
	 * HmacSHA1消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA1密钥
	 * @return String base64编码的HmacSHA1消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA1Base64String(byte[] data, byte[] key)throws Exception{
		return Base64.encodeBase64String(encodeHmacSHA1(data, key));
	}
	
	/**
	 * 初始化HmacSHA256密钥
	 * @return byte[] HmacSHA256密钥
	 * @throws Exception
	 */
	public static byte[] initHmacSHA256() throws Exception{
		return initKey(KeyGenType.HMACSHA256);
	}
	
	/**
	 * HmacSHA256消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA256密钥
	 * @return byte[] HmacSHA256消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeHmacSHA256(byte[] data, byte[] key)throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, KeyGenType.HMACSHA256.getValue());
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * HmacSHA256消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA256密钥
	 * @return String 16进制HmacSHA256消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA256HexString(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacSHA256(data, key));
	}
	
	/**
	 * HmacSHA256消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA256密钥
	 * @return String base64编码的HmacSHA256消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA256Base64String(byte[] data, byte[] key)throws Exception{
		return Base64.encodeBase64String(encodeHmacSHA256(data, key));
	}
	
	/**
	 * 初始化HmacSHA384密钥
	 * @return byte[] HmacSHA384密钥
	 * @throws Exception
	 */
	public static byte[] initHmacSHA384() throws Exception{
		return initKey(KeyGenType.HMACSHA384);
	}
	
	/**
	 * HmacSHA384消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA384密钥
	 * @return byte[] HmacSHA384消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeHmacSHA384(byte[] data, byte[] key)throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, KeyGenType.HMACSHA384.getValue());
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * HmacSHA384消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA384密钥
	 * @return String 16进制HmacSHA384消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA384HexString(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacSHA384(data, key));
	}
	
	/**
	 * HmacSHA384消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA384密钥
	 * @return String base64编码的HmacSHA384消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA384Base64String(byte[] data, byte[] key)throws Exception{
		return Base64.encodeBase64String(encodeHmacSHA384(data, key));
	}
	
	/**
	 * 初始化HmacSHA512密钥
	 * @return byte[] HmacSHA512密钥
	 * @throws Exception
	 */
	public static byte[] initHmacSHA512() throws Exception{
		return initKey(KeyGenType.HMACSHA512);
	}
	
	/**
	 * HmacSHA512消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA512密钥
	 * @return byte[] HmacSHA512消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeHmacSHA512(byte[] data, byte[] key)throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, KeyGenType.HMACSHA512.getValue());
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * HmacSHA512消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA512密钥
	 * @return String 16进制HmacSHA512消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA512HexString(byte[] data, byte[] key)throws Exception{
		return Hex.encodeHexString(encodeHmacSHA512(data, key));
	}
	
	/**
	 * HmacSHA512消息摘要
	 * @param data 待做摘要处理的数据
	 * @param key HmacSHA512密钥
	 * @return String base64编码的HmacSHA512消息摘要
	 * @throws Exception
	 */
	public static String encodeHmacSHA512Base64String(byte[] data, byte[] key)throws Exception{
		return Base64.encodeBase64String(encodeHmacSHA512(data, key));
	}
}

enum KeyGenType{
	
	HMACMD5("HmacMD5"),
	HMACSHA1("HmacSHA1"),
	HMACSHA256("HmacSHA256"),
	HMACSHA384("HmacSHA384"),
	HMACSHA512("HmacSHA512")
	;
	
	private String value;
	
	KeyGenType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
