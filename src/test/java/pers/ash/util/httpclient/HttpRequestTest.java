package pers.ash.util.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpRequestTest {

	@Test
	public void testHttpGet() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response = client.execute(httpGet);
		printRequestHeaders(httpGet);
		printResponseHeaders(response);
		print(response);
	}
	
	/**
	 * post与put继承了HttpEntityEnclosingRequestBase,可以发送entity
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void testHttpPost() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://www.baidu.com");
		httpPost.setEntity(new StringEntity("string content"));
		CloseableHttpResponse response = client.execute(httpPost);
		printRequestHeaders(httpPost);
		printResponseHeaders(response);
		print(response);
	}
	
	/**
	 * post与put继承了HttpEntityEnclosingRequestBase,可以发送entity
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void testHttpPut() throws ClientProtocolException, IOException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", "zhangsan"));
		params.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut("http://www.baidu.com");
		httpPut.setEntity(formEntity);
		CloseableHttpResponse response = client.execute(httpPut);
		printRequestHeaders(httpPut);
		printResponseHeaders(response);
		print(response);
	}
	
	@Test
	public void testHttpDelete() throws ClientProtocolException, IOException{
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete("http://www.baidu.com");
		CloseableHttpResponse response = client.execute(httpDelete);
		printRequestHeaders(httpDelete);
		printResponseHeaders(response);
		print(response);
	}
	
	/**
	 * head请求与get请求一样,但只会返回消息头,不会返回消息体
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void testHttpHead() throws ClientProtocolException, IOException{
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpHead httpHead = new HttpHead("http://www.baidu.com");
		CloseableHttpResponse response = client.execute(httpHead);
		printRequestHeaders(httpHead);
		printResponseHeaders(response);
		print(response);
	}
	
	public void print(CloseableHttpResponse response) throws ParseException, IOException{
		HttpEntity entity = response.getEntity();
		if(null != entity){
			System.out.println(EntityUtils.toString(entity));
		}
		response.close();
	}
	
	public void printResponseHeaders(CloseableHttpResponse response){
		HeaderIterator itr = response.headerIterator();
		System.out.println("Response Headers");
		while(itr.hasNext()){
			Header header = itr.nextHeader();
			System.out.println("    " + header.toString());
		}
	}
	
	public void printRequestHeaders(HttpUriRequest request){
		HeaderIterator itr = request.headerIterator();
		System.out.println("Request Headers");
		while(itr.hasNext()){
			Header header = itr.nextHeader();
			System.out.println("    " + header.toString());
		}
	}
}
