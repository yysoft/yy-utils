/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-12-10
 */
package net.caiban.utils.param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

//import com.zz91.util.cache.MemcachedUtils;
//import com.zz91.util.domain.Param;
//import com.zz91.util.lang.StringUtils;


/**
 * 一个用来管理参数的工具
 * 
 * @author root
 * 
 *         created on 2010-12-10
 */
@Deprecated
public class ParamUtils {
	private Logger LOG = Logger.getLogger(ParamUtils.class);
	
	private static ParamUtils _instance = null;
	
	public final static int EXPIRATION = 0;
	private static Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
	
	@SuppressWarnings("unused")
	private static String MEM_TYPE = "memory";
	
	private static String PREFIX = "param@";
	
	
	private ParamUtils(){
		
	}
	
	synchronized public static ParamUtils getInstance() {
		if (_instance == null) {
			_instance = new ParamUtils();
		}
		return _instance;
	}
	
	public void putObject(String key, Object value) {
//		if ("memcached".equals(MEM_TYPE)) {
//			MemcachedUtils.getInstance().getClient().set(key, EXPIRATION, value);
//		} else {
			paramMap.put(key, value);
//		}
	}
	
	public Object holdObject(String key) {
//		if ("memcached".equals(MEM_TYPE)) {
//			return MemcachedUtils.getInstance().getClient().get(key);
//		}
		return paramMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public void init(List<Param> paramList, String type){
		LOG.debug("Initializing params...");
		if(!Strings.isNullOrEmpty(type)){
			MEM_TYPE = type;
		}
		
		for(Param p:paramList){
			String mapkey=PREFIX+p.getTypes();
			Map<String, String> map=(Map<String, String>) holdObject(mapkey);
			if(map==null ){
				map = new LinkedHashMap<String, String>();
			}
			map.put(p.getKey(), p.getValue());
			putObject(mapkey, map);
		}
		LOG.debug("Initializing params end...");
	}
	
	public String getValue(String type, String key){
		Map<String, String> m=getChild(type);
		if(m==null){
			return null;
		}
		return m.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getChild(String type){
		return (Map<String, String>) holdObject(PREFIX+type);
	}

}
