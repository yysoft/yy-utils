package net.caiban.utils.aliyun;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.google.common.base.Preconditions;

public class OssUtil {
	
	public static enum ENDPOINT{
		
		qingdao("http://oss-cn-qingdao.aliyuncs.com"),
		qingdao_internal("oss-cn-qingdao-internal.aliyuncs.com"),
		
		beijing("http://oss-cn-beijing.aliyuncs.com"),
		beijing_internal("http://oss-cn-beijing-internal.aliyuncs.com"),
		
		hangzhou("http://oss-cn-hangzhou.aliyuncs.com"),
		hangzhou_internal("http://oss-cn-hangzhou-internal.aliyuncs.com"),
		
		hongkong("http://oss-cn-hongkong.aliyuncs.com"),
		hongkong_internal("http://oss-cn-hongkong-internal.aliyuncs.com"),
		
		shenzhen("http://oss-cn-shenzhen.aliyuncs.com"),
		shenzhen_internal("http://oss-cn-shenzhen-internal.aliyuncs.com"),
		
		shanghai("http://oss-cn-shanghai.aliyuncs.com"),
		shanghai_internal("http://oss-cn-shanghai-internal.aliyuncs.com"),
		
		guigu("http://oss-us-west-1.aliyuncs.com"),
		guigu_internal("http://oss-cn-west-1-internal.aliyuncs.com"),
		
		yatai("http://oss-ap-southeast-1.aliyuncs.com"),
		yatai_internal("http://oss-ap-southeast-1-internal.aliyuncs.com"),
		
		;
		
		String endpoint;
		
		private ENDPOINT(String endpoint){
			this.endpoint = endpoint;
		}
		
		public String getEndpoint(){
			return this.endpoint;
		}
		
	}
	
	private static String accessId;
	private static String accessKey;
	
	private static Map<String, OSSClient> clientMap = new ConcurrentHashMap<String, OSSClient>();
	
	private static InetAddress ia;
	private static String hostName;
	public static int TIMEOUT = 60*5;	//在浏览器与服务器失去通讯5分钟后清除缓存
	
	private static ClientConfiguration conf;
	
	static{
		try{ia = InetAddress.getLocalHost();}catch(UnknownHostException e){}
		hostName=ia.getHostName();
		//ossClient = new OSSClient(Constants.ossEndpoint, Constants.accessId, Constants.accessKey);
		conf = new ClientConfiguration();
		//设置HTTP最大连接数(默认为50)
		conf.setMaxConnections(100);
		//设置TCP连接超时时间(默认为50000毫秒)
		conf.setConnectionTimeout(10000);
	}
	
	public static String getHostName() {
		return hostName;
	}
	
	public static void init(String accessId, String accessKey){
		OssUtil.accessId = accessId;
		OssUtil.accessKey = accessKey;
	}
	
	synchronized public static OSSClient getClient(ENDPOINT endpoint) {
		
		Preconditions.checkNotNull(endpoint);
		
		if(clientMap.get(endpoint.name())==null){
			clientMap.put(endpoint.name(), new OSSClient(endpoint.getEndpoint(), OssUtil.accessId, OssUtil.accessKey, conf));
		}
		
		return clientMap.get(endpoint.name());
	}
	
	public static void main(String[] args) {
		OssUtil.init("id", "key");
		
		OSSClient client = OssUtil.getClient(ENDPOINT.hangzhou_internal);
		System.out.println(client.getEndpoint());
	}
	
}
