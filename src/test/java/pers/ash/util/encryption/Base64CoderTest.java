package pers.ash.util.encryption;

import static org.junit.Assert.*;

import org.junit.Test;

public class Base64CoderTest {

	private String data = "使用Base64编码算法";
	private String url = "http://www.baidu.com";
	
	@Test
	public void testEncode() throws Exception {
		String encodedData = Base64Coder.encode(data);
		String decodedData = Base64Coder.decode(encodedData);
		System.out.println("原始数据: " + data);
		System.out.println("base64编码后: " + encodedData);
		System.out.println("base64解码后: " + decodedData);
		assertEquals(data, decodedData);
	}

	@Test
	public void testEncodeSafe() throws Exception {
		String data = "gyyjqfxhym1001123456789ppooiiuuyyttrreewwqqasdfgbnyhnujmik,olrfvedcwsx";
		String encodedData = Base64Coder.encodeSafe(data);
		String decodedData = Base64Coder.decode(encodedData);
		System.out.println("原始数据: " + data);
		System.out.println("base64编码后: " + encodedData);
		System.out.println("base64解码后: " + decodedData);
		assertEquals(data, decodedData);
	}

	@Test
	public void testEncodeUrl() throws Exception {
		String encodedUrl = Base64Coder.encodeUrl(url);
		String decodedUrl = Base64Coder.decode(encodedUrl);
		System.out.println("原始数据: " + data);
		System.out.println("base64编码后: " + encodedUrl);
		System.out.println("base64解码后: " + decodedUrl);
		assertEquals(url, decodedUrl);
	}
	
	@Test
	public void test(){
		String str = "Fg02WKQAat\\/BLAc9CUczH0GwngVR1ex0Hxy1NBCLzIswMJdNJ6jwlH2DG3Kk4s4W";
		System.out.println(str.length());
	}

}
