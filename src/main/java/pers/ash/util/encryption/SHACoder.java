package pers.ash.util.encryption;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * SHA信息摘要算法<br>
 * 依赖于commons-codec
 * @author Ash Crismon
 *
 */
public abstract class SHACoder {

	/**
	 * SHA-1消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA(byte[] data) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA");
		return md.digest(data);
	}
	
	/**
	 * SHA-1消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String 16进制编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHAHexString(byte[] data) throws Exception{
		return Hex.encodeHexString(encodeSHA(data));
	}
	
	/**
	 * SHA-1消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String base64编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHABase64String(byte[] data) throws Exception{
		return Base64.encodeBase64String(encodeSHA(data));
	}
	
	/**
	 * SHA-256消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA256(byte[] data) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(data);
	}
	
	/**
	 * SHA-256消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String 16进制编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA256HexString(byte[] data) throws Exception{
		return Hex.encodeHexString(encodeSHA256(data));
	}
	
	/**
	 * SHA-256消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String base64编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA256Base64String(byte[] data) throws Exception{
		return Base64.encodeBase64String(encodeSHA256(data));
	}
	
	/**
	 * SHA-384消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA384(byte[] data) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		return md.digest(data);
	}
	
	/**
	 * SHA-384消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String 16进制编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA384HexString(byte[] data) throws Exception{
		return Hex.encodeHexString(encodeSHA384(data));
	}
	
	/**
	 * SHA-384消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String base64编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA384Base64String(byte[] data) throws Exception{
		return Base64.encodeBase64String(encodeSHA384(data));
	}
	
	/**
	 * SHA-512消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA512(byte[] data) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		return md.digest(data);
	}
	
	/**
	 * SHA-512消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String 16进制编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA512HexString(byte[] data) throws Exception{
		return Hex.encodeHexString(encodeSHA512(data));
	}
	
	/**
	 * SHA-512消息摘要
	 * @param data 待做消息摘要处理的数据
	 * @return String base64编码后的消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA512Base64String(byte[] data) throws Exception{
		return Base64.encodeBase64String(encodeSHA512(data));
	}
}
