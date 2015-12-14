package pers.ash.util.httpclient;

import java.io.File;
import java.io.IOException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpEntityTest {

	/**
	 * 自包含实体(self-contained entity)
	 * 可以重复读取(repeatable)
	 * @throws IOException 
	 */
	@Test
	public void testStringEntity() throws IOException{
		String content = "string content";
		ContentType contentType = ContentType.create("text/plain","UTF-8");
		StringEntity entity = new StringEntity(content, contentType);
		System.out.println(entity.getContentType());
		System.out.println("Content-Length : " + entity.getContentLength());
		System.out.println("Content-Encoding : " + entity.getContentEncoding());
		System.out.println("Content : " + EntityUtils.toString(entity));
	}
	
	/**
	 * 自包含实体(self-contained entity)
	 * 可以重复读取(repeatable)
	 * @throws IOException 
	 */
	@Test
	public void testFileEntity() throws IOException{
		File file = new File("src/main/resources/test.properties");
		ContentType contentType = ContentType.create("text/plain","UTF-8");
		FileEntity entity = new FileEntity(file, contentType);
		entity.setChunked(true);
		System.out.println(entity.getContentType());
		System.out.println("Content-Length : " + entity.getContentLength());
		System.out.println("Content-Encoding : " + entity.getContentEncoding());
		System.out.println("Content : " + EntityUtils.toString(entity));
	}
}
