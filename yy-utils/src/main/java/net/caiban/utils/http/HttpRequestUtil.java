/**
 * 
 */
package net.caiban.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

/**
 * @author parox
 *
 */
public class HttpRequestUtil {
	
	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private static CloseableHttpClient client = null;
	
	private static IdleConnectionMonitorThread monitor = null;
	
	private static Logger LOG = Logger.getLogger(HttpRequestUtil.class);
	
	private final static String DEFAULT_ENCODE="utf-8";
	
	private static synchronized CloseableHttpClient getClient(){
		
		if(client==null){
			cm.setMaxTotal(20);
			cm.setDefaultMaxPerRoute(20);
			
			HttpHost localhost = new HttpHost("localhost", 80);
			cm.setMaxPerRoute(new HttpRoute(localhost), 50);
			
			client = HttpClients.custom().setConnectionManager(cm).build();
			
			monitor = new IdleConnectionMonitorThread(cm);
			monitor.start();
		}
		
		return client;
	}
	
	public static CloseableHttpResponse httpGetResponse(HttpGet httpGet) throws ClientProtocolException, IOException{
		
		HttpContext context = HttpClientContext.create();
		
		return getClient().execute(httpGet, context);
//		try {
//			return response.getEntity();
//		}finally{
////			response.close();
//		}
	}
	
	public static CloseableHttpResponse httpGetResponse(String url) throws ClientProtocolException, IOException{
		
		HttpGet httpGet = new HttpGet(url);
		
		return httpGetResponse(httpGet);
	}
	
	public static String httpGet(String url){
		
		return httpGet(url, DEFAULT_ENCODE);
	}
	
	public static String httpGet(String url, String encode){
		
		try {
			
			CloseableHttpResponse response = httpGetResponse(url);
			
			try {
				
				HttpEntity entity = response.getEntity();
				
				if(entity!=null){
					InputStream is = entity.getContent();
					return httpResponseAsString(is, encode);
				}
				
			} finally{
				response.close();
			}
			
		} catch (ClientProtocolException e) {
			LOG.error("Error occurred when get url. Message:"+e.getMessage(), e);
		} catch (IOException e) {
			LOG.error("Error occurred when get url. Message:"+e.getMessage(), e);
		}
		return null;
	}
	
	public static CloseableHttpResponse httpPostResponse(HttpPost httpPost) throws ClientProtocolException, IOException{
		HttpContext context = HttpClientContext.create();
		return getClient().execute(httpPost, context);
	}
	
	public static CloseableHttpResponse httpPostResponse(String url) throws ClientProtocolException, IOException{
		
		return httpPostResponse(url, null, Consts.UTF_8);
	}
	
	public static CloseableHttpResponse httpPostResponse(String url, List<NameValuePair> params) throws ClientProtocolException, IOException{
		return httpPostResponse(url, params, Consts.UTF_8);
	}
	
	public static CloseableHttpResponse httpPostResponse(String url, List<NameValuePair> params, Charset charset) throws ClientProtocolException, IOException{
		
		HttpPost httpPost = new HttpPost(url);
		
		if(params!=null){
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
			httpPost.setEntity(entity);
		}
		
		return httpPostResponse(httpPost);
	}

	public static String httpPost(String url){
		return httpPost(url, null, Consts.UTF_8, DEFAULT_ENCODE);
	}
	
	public static String httpPost(String url, List<NameValuePair> params){
		return httpPost(url, params, Consts.UTF_8, DEFAULT_ENCODE);
	}
	
	public static String httpPost(String url, List<NameValuePair> params, Charset postCharset, String readEncode){
		try {
			CloseableHttpResponse response = httpPostResponse(url, params, postCharset);
			try {
				
				HttpEntity entity = response.getEntity();
				
				if(entity!=null){
					InputStream is = entity.getContent();
					return httpResponseAsString(is, readEncode);
				}
				
			} finally {
				response.close();
			}
			
		} catch (ClientProtocolException e) {
			LOG.error("Error occurred when get url. Message:"+e.getMessage(), e);
		} catch (IOException e) {
			LOG.error("Error occurred when get url. Message:"+e.getMessage(), e);
		}
		return null;
	}
	
	public static void shutdown(){
		if(client!=null){
			try {
				client.close();
			} catch (IOException e) {
				LOG.error("Error occurred when close http client. Message:"+e.getMessage(), e);
			}
		}
		
		monitor.shutdown();
	}
	
	public static String httpResponseAsString(InputStream is, String encode)
			throws IOException {
		if (is == null) {
			return "";
		}

		StringBuffer out = new StringBuffer();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, encode));
			while ((line = reader.readLine()) != null) {
				out.append(line).append("\n");
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		
		return out.toString();
	}
	
	public static void main(String[] args) {
		
		for(int i = 0; i<100; i++){
			Thread thread = new VisitBaidu();
			thread.start();
		}
		
		System.out.println("CLOSE");
	}
	
	static class VisitBaidu extends Thread {
		public void run(){
			System.out.println(HttpRequestUtil.httpGet("http://www.baidu.com"));
		}
	}
	

}
