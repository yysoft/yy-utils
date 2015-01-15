/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-12-13
 */
package net.caiban.utils.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;

/**
 * @author mays (mays@zz91.net)
 * 
 *         created on 2010-12-13
 */
public class MemcachedUtils {

	private static Logger LOG = Logger.getLogger(MemcachedUtils.class);

	private String DEFAULT_PROP = "cache.properties";

	private static MemcachedUtils _instances = null;
	
	private MemcachedClient client;

	public void MemcachedClient() {
		
	}

	public static synchronized MemcachedUtils getInstance() {
		if (_instances == null) {
			_instances = new MemcachedUtils();
		}
		return _instances;
	}

	/**
	 * 使用默认配置初始化memcached客户端
	 */
	public void init() {
		init(DEFAULT_PROP);
	}

	/**
	 * 初始化缓存服务客户端
	 * 
	 * @param properties
	 *            :配置文件的名字或者完整的配置文件路径 例：cache.properites 或
	 *            /usr/config/cache.properties
	 */
	public void init(String properties) {
		// 从配置文件读取缓存服务器信息
		LOG.debug("Loading cache properties:" + properties);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				properties);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			LOG.error("An error occurred when load cached properties:"
					+ properties, e);
		}

		// 初始化memcache client
		createClient(p.getProperty("memcached.server"));
	}

	/**
	 * 创建一个memcache客户端连接
	 * 
	 * @param clientKey
	 *            ： 客户端key,在获取客户端对象时需要使用key,key不能与已经存在的key重复
	 * @param serverAddress
	 *            ： memcached服务器及端口地址，多个服务器用空格隔开
	 *            例："127.0.0.1:11211 localhost:11210"
	 */
	public void createClient(String serverAddress) {
		try {
			client = new MemcachedClient(AddrUtil.getAddresses(serverAddress));
			LOG.debug("Memcache client initialized.");
		} catch (IOException e) {
			LOG.error("An error occurred when initialize memcached client", e);
		}
	}

	/**
	 * 关闭全部memcached客户端连接
	 */
	public void shutdownClient() {
		LOG.debug("shutdown memcached client...");
		client.shutdown();
		LOG.debug("client has been closed.");
	}

	/**
	 * 通过key获取memcached客户端
	 * 
	 * @param clientKey
	 *            :要获取的客户端key 例：
	 *            MemcachedUtils.getClient().set("user",0,"user profile");
	 * @return
	 */
	public MemcachedClient getClient() {
		return client;
	}

}
