package pers.ash.util.httpclient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.pool.BasicConnFactory;
import org.apache.http.impl.pool.BasicConnPool;
import org.apache.http.impl.pool.BasicPoolEntry;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTest {

	/**
	 * 连接超时时间,在这个时间内无法连接到服务器,就会抛出connect timeout<br>
	 * A timeout while connecting to an HTTP server or waiting for an<br>
	 * available connection from an HttpConnectionManager.
	 */
	private int CONNECTION_TIMEOUT = 2 * 1000;
	
	/**
	 * 请求响应时间,连接已经建立,在这个时间内没有数据回复,就会抛出socket timeout<br>
	 * Signals that a timeout has occurred on a socket read or accept.
	 */
	private int SOCKET_TIMEOUT = 3 * 1000;
	
	private RequestConfig requestConfig;
	
	@Before
	public void initConfig(){
		requestConfig = RequestConfig.custom()//
				.setConnectTimeout(CONNECTION_TIMEOUT)//
				.setSocketTimeout(SOCKET_TIMEOUT)//
				.build();
	}
	
	/**
	 * 测试连接超时
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test(expected = ConnectTimeoutException.class)
	public void testConnectionTimeout() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.facebook.com");
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		response.close();
	}
	
	/**
	 * 测试持久连接
	 * Connection: Keep-Alive
	 * Keep-Alive: max=5,timeout=120
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testConnectionKeepAlive() throws ClientProtocolException, IOException {
		ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {

			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator itr = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while(itr.hasNext()){
					HeaderElement element = itr.nextElement();
					String name = element.getName();
					String value = element.getValue();
					if(name.equalsIgnoreCase("timeout") && !StringUtils.isBlank(value)){
						return Long.parseLong(value) * 1000;
					}
				}
				return 5 * 1000;
			}

		};
		CloseableHttpClient client = HttpClients.custom()
		        .setKeepAliveStrategy(keepAliveStrategy)//
		        .build();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response = client.execute(httpGet);
		
		printRequestHeaders(httpGet);
		printResponseHeaders(response);
		response.close();
	}
	
	/**
	 * 由于http连接三次握手开销较大,所以建立持久连接,实现连接复用
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	@Test
	public void testConnectionManager() throws InterruptedException, ExecutionException,
			IOException {
		HttpClientContext context = HttpClientContext.adapt(new BasicHttpContext());
		HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		HttpRoute route = new HttpRoute(new HttpHost("localhost", 80));
		ConnectionRequest connRequest = connManager.requestConnection(route, null);
		HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
		try {
			if (!conn.isOpen()) {
				connManager.connect(conn, route, 1000, context);
				connManager.routeComplete(conn, route, context);
			}
		} finally {
			connManager.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
		}
	}
	
	@Test
	public void test(){
	}
	
	@Test
	public void testBasicConnPool() throws InterruptedException, ExecutionException, HttpException, IOException{
		boolean reusable = false;
		HttpProcessor httpproc = HttpProcessorBuilder.create()
	            .add(new RequestContent())
	            .add(new RequestTargetHost())
	            .add(new RequestConnControl())
	            .add(new RequestUserAgent("Test/1.1"))
	            .add(new RequestExpectContinue(true)).build();

	    HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
		HttpHost target = new HttpHost("localhost");
		BasicConnPool connPool = new BasicConnPool(new BasicConnFactory());
		connPool.setMaxTotal(200);
		connPool.setDefaultMaxPerRoute(10);
		connPool.setMaxPerRoute(target, 20);
		
		ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
		Future<BasicPoolEntry> future = connPool.lease(target, null);
		BasicPoolEntry poolEntry = future.get();
		HttpClientConnection conn = poolEntry.getConnection();
		
		HttpCoreContext coreContext = HttpCoreContext.create();
        coreContext.setTargetHost(target);

        BasicHttpRequest request = new BasicHttpRequest("GET", "/");
        System.out.println(">> Request URI: " + request.getRequestLine().getUri());

        httpexecutor.preProcess(request, httpproc, coreContext);
        HttpResponse response = httpexecutor.execute(request, conn, coreContext);
        httpexecutor.postProcess(response, httpproc, coreContext);

        System.out.println("<< Response: " + response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));

        reusable = connStrategy.keepAlive(response, coreContext);
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
