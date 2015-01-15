package net.caiban.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author mays
 *
 */
public class PropertiesUtil {

	public final static String CHARSET_UTF8="utf-8";
	public final static String CHARSET_GBK="gbk";
	public final static String CHARSET_ISO_8859_1="ISO-8859-1";
	
	private final static String FILE_PREFIX="file:"; 
	
	@SuppressWarnings("rawtypes")
	public static Map<String, String> read(String file,String charsetName) throws IOException {
		if (charsetName==null || charsetName.trim().length()==0){
			charsetName=CHARSET_GBK;
		}
		Map<String, String> map = new HashMap<String, String>();
		InputStream is =null;
		if(file.startsWith("file:"))
			is=new FileInputStream(new File(file.substring(5)));
		else
			is=PropertiesUtil.class.getClassLoader().getResourceAsStream(file);
		Properties properties = new Properties();
		properties.load(is);
		Enumeration en = properties.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String code = new String(properties.getProperty(key).getBytes(
					CHARSET_ISO_8859_1), charsetName);
			map.put(key, code);
		}
		return map;
	}
	
	public static Map<String, String> classpathRead(String file, String charsetName) throws IOException{
		return read(file, charsetName);
	}
	
	public static Map<String, String> classpathRead(String file) throws IOException{
		return read(file, CHARSET_UTF8);
	}
	
	public static Map<String, String> fileRead(String file, String charsetName) throws IOException {
		return read(FILE_PREFIX+file, charsetName);
	}
	
	public static Map<String, String> fileRead(String file) throws IOException {
		return read(FILE_PREFIX+file, CHARSET_UTF8);
	}
}
