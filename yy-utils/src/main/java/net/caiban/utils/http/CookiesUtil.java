/**
 * 
 */
package net.caiban.utils.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author mays (mays@caiban.net)
 *
 */
public class CookiesUtil {
	
	/**
	 * 从cookie中得到值
	 * 
	 * @param request
	 * @param key
	 *            :cookie名称
	 * @param domain
	 *            :域名
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key,
			String domain) {
		
//		domain = StringUtils.isEmpty(domain)?request.getServerName():domain;
		
		if (key == null || "".equals(key)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (key.equals(c.getName())) {
//					if(StringUtils.isNotEmpty(domain) ){
//						if(domain.equals(c.getDomain())){
							return c.getValue();
//						}
//					}else{
//						return c.getValue();
//					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 设置cookie
	 * 
	 * @param response
	 *            : 从外部传进来的response对象,不可以为null
	 * @param key
	 *            : cookie的健
	 * @param value
	 *            : cookie的值
	 * @param domain
	 *            : cookie所在的域,可以为null,为null时按时默认的域存储
	 * @param maxAge
	 *            : cookie最大时效,以秒为单位,为null时表示不设置最大时效,按照浏览器进程结束
	 */
	public static void setCookie(HttpServletResponse response, String key,
			String value, String domain, Integer maxAge) {
		Cookie c = new Cookie(key, value);
		if (domain != null && domain.length() > 0) {
			c.setDomain(domain);
		}
		if (maxAge != null) {
			c.setMaxAge(maxAge);
		}
		c.setPath("/");
		response.addCookie(c);
	}
	
	public static void main(String[] args) {
		
	}
}
