package net.caiban.utils.cache;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.caiban.utils.file.PropertiesUtil;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @author parox
 *
 *	jedis prject: https://github.com/xetorthio/jedis <br />
 *	
 *	Example: <br />
 *	Jedis jedis = null;
 *	try {
 *		 jedis = JedisUtil.getJedis();
 *	
 *		Set<String> tags = jedis.keys("*");
 *	
 *	} catch (Exception e) {
 *		e.printStackTrace();
 *	}finally{
 *		JedisUtil.getPool().returnResource(jedis);
 *	}
 * 
 */
public class JedisUtil {
	
	private static JedisPool pool;
	
	private static Lock lock = new ReentrantLock();
	
	private static Logger LOG = Logger.getLogger(JedisUtil.class);
	
	private static final String DEFAULT_PROP="redis.properties";
	
	private static final String DEFAULT_CHARSET="utf-8";
	
	public static JedisPool getPool(){
		if(pool == null){
			createPool(null, DEFAULT_CHARSET);
		}
		
		return pool;
	}
	
	public static void initPool(String prop){
		if(prop==null){
			prop=DEFAULT_PROP;
		}
		createPool(prop, DEFAULT_CHARSET);
	}
	
	private static void createPool(String prop, String charset){
		if(pool == null){
			LOG.debug("Jedis pool is not exist, lock and create pool.");
			
			lock.lock();
			
			if(pool!=null){
				lock.unlock();
			}
			
			try {
				
				Map<String, String> conf = PropertiesUtil.read(prop, charset);
				String host = conf.get("redis.server");
				String confPort = conf.get("redis.server.port");
				
				int port = (confPort==null || "".equals(confPort))?Protocol.DEFAULT_PORT:Integer.valueOf(confPort);
				
				JedisPoolConfig config  =new JedisPoolConfig();
				//TODO config by properties
				pool = new JedisPool(config, host, port);
				
			} catch (IOException e) {
				LOG.error("Error occurred when create jedis pool.", e);
			}finally{
				lock.unlock();
			}
		}
		
	}
	
//	public static Jedis getJedis(){
//		try {
//			Jedis jedis = getPool().getResource();
//			return jedis;
//		} catch (Exception e) {
//			LOG.error("Error occurred when get jedis client from pool.", e);
//		}
//		return null;
//	}

	public static void returnJedis(Jedis jedis){
		if(jedis==null){
			return ;
		}
		getPool().returnResource(jedis);
	}
	
	public static void returnBrokenJedis(Jedis jedis){
		if(jedis!=null){
			getPool().returnBrokenResource(jedis);
		}
	}
}
