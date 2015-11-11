package pers.ash.util.encryption;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64编码算法<br>
 * 依赖commons-codec
 * @author Ash Crismon
 *
 */
public abstract class Base64Coder {

	public static final String ENCODING = "UTF-8";
	
	/**
	 * Base64编码
	 * @param data 待编码数据
	 * @return String 编码后的数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception{
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}
	
	/**
	 * 遵循RFC 2045标准<br>
	 * 每行为76个字符,行末加入一个回车换行符('\r\n') 
	 * @param data 待编码数据
	 * @return String 编码后数据
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception{
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);
		return new String(b, ENCODING);
	}
	
	/**
	 * Base64解码
	 * @param data 待解码数据
	 * @return String 解码后数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception{
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}
	
	/**
	 * "+","/"和"="不允许出现在URL中<br>
	 * RFC 4648中给出了相应的替代符号 "-"和"_"<br>
	 * Commons Codec不使用填充符，使用不定长的Base64编码
	 * @param url 待编码url
	 * @return String 编码后的url
	 * @throws Exception
	 */
	public static String encodeUrl(String url) throws Exception{
		byte[] b = Base64.encodeBase64URLSafe(url.getBytes(ENCODING));
		return new String(b, ENCODING);
	}
}
