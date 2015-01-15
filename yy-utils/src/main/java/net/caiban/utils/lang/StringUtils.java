/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-17 by Ryan.
 */
package net.caiban.utils.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ryan
 * 
 */
public class StringUtils {
	/**
	 * 获取分页页码
	 * 
	 * @param p
	 * @return
	 */
	public static String getPageIndex(String p) {
		if (isEmpty(p) || !isNumber(p)) {
			return "1";
		}
		return p;
	}

	public static boolean isContains(String[] strs, String s) {
		// 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串

		for (int i = 0; i < strs.length; i++) {
			if (strs[i].indexOf(s) != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
				return true;// 查找到了就返回真，不在继续查询
			}
		}
		return false;// 没找到返回false
	}

	/**
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {
		if (isEmpty(s)) {
			return false;
		} else {
			for (int i = 0; i < s.length(); i++) {
				if (!((s.charAt(i) >= '0') && (s.charAt(i) <= '9'))) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * @param s
	 * @return
	 */
	public static boolean isIp(String s) {
		String strMatch = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
		Pattern ParsePattern = Pattern.compile(strMatch);
		Matcher ParseMatcher = ParsePattern.matcher(s);
		return ParseMatcher.find();
	}

	/**
	 * @param s
	 * @return
	 */
	public static boolean isDomainName(String s) {
		String strMatch = "[a-zA-Z0-9]+([a-zA-Z0-9\\-\\.]+)?\\.(com|cn|org|net|mil|edu|COM|ORG|NET|MIL|EDU)";
		Pattern ParsePattern = Pattern.compile(strMatch);
		Matcher ParseMatcher = ParsePattern.matcher(s);
		return ParseMatcher.find();
	}

	/**
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s) {
		String strMatch = "([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})";
		Pattern ParsePattern = Pattern.compile(strMatch);
		Matcher ParseMatcher = ParsePattern.matcher(s);
		return ParseMatcher.find();
	}

	/**
	 * @param d
	 * @param pL
	 * @return
	 */
	public static String getStandardDouble(double d, int pL) {
		String format = "0.";
		for (int i = 0; i < pL; i++)
			format += "0";
		return ((new DecimalFormat(format)).format(d));
	}

	/**
	 * 获取N位随机数字的字符串
	 * @param n
	 * @return
	 */
	public static String getRandValue(int n) {
		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 返回非null的字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String getNotNullValue(Object s) {
		return s == null ? "" : s.toString();
	}

	/**
	 * 将"1,2,3,4"格式的字符串转换成integer数组
	 * 
	 * @param strArray
	 * @return
	 */
	public static Integer[] StringToIntegerArray(String strArray) {
		List<Integer> ids = new ArrayList<Integer>();
		String[] idstrArray = strArray.split(",");
		if (idstrArray.length > 0) {
			for (String str : idstrArray) {
				if (isNumber(str))
					ids.add(Integer.valueOf(str));
			}
		}
		return ids.toArray(new Integer [0]);
	}

	/**
	 * 过滤sql句的敏感字符（'）
	 * 
	 * @param sql
	 * @return
	 */
	public static String filterSql(String sql) {
		return sql.replace("'", "''");
	}

	/**
	 * 将过滤sql句的敏感字符（'）
	 * 
	 * @param sql
	 * @return
	 */
	@Deprecated
	public static String getCorrentSql(Object sql) {
		if (sql != null) {
			String s = sql.toString();
			return s.replace("\\", "\\\\").replace("'", "''");
		} else {
			return null;
		}

	}

	/**
	 * 将过滤sql句的敏感字符（'）,在调用处不需要加单引号，由这里添加，可以防止将null值做为'null'字符串插入
	 * 
	 * @param sql
	 * @return
	 */
	public static String getNewCorrentSql(Object sql) {
		if (sql != null) {
			String s = sql.toString();
			return "'" + s.replace("\\", "\\\\").replace("'", "''") + "'";
		} else {
			return null;
		}

	}

	/**
	 * 编码URL带的参数
	 * 
	 * @param str
	 *            参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decryptUrlParameter(String str) throws UnsupportedEncodingException {
		if (isEmpty(str)) {
			return "";
		}
		return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	}

	/**
	 * 加密URL带的参数
	 * 
	 * @param str
	 *            参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptUrlParameter(String str) throws UnsupportedEncodingException {
		if (isEmpty(str)) {
			return "";
		}
		return new String(str.getBytes("UTF-8"), "ISO-8859-1");
	}

	/**
	 * 控制字符串显示长度，如果过长则按length截取并加"..."
	 * 
	 * @param s
	 *            待显示的字符串
	 * @param length
	 *            能显示的最大长度，英文算1个单位长度，中文字符2单位长度
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String controlLength(String s, Integer length)
			throws UnsupportedEncodingException {
		if (s != null) {
			Integer size = 0;
			for (Integer index = 0; index < s.length(); index++) {
				Integer i = s.substring(index, index + 1).getBytes("UTF-8").length;
				if (size + i > length) {
					return s.substring(0, index) + "...";
				} else {
					size += i;
				}
			}
		}
		return s;
	}

	public static String controlLengthAddColon(String s, Integer length)
			throws UnsupportedEncodingException {
		if (s != null) {
			Integer size = 0;
			for (Integer index = 0; index < s.length(); index++) {
				Integer i = s.substring(index, index + 1).getBytes("UTF-8").length;
				if (size + i > length) {
					return s.substring(0, index);
				} else {
					size += i;
				}
			}
		}
		return s;
	}

	/**
	 * 去除字符串中的HTML标签及一些空白字符
	 * 
	 * @param htmlString
	 * @return
	 */
	public static String removeHTML(String htmlString) {
		// Remove HTML tag from java String
		String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");

		// Remove Carriage return from java String
		noHTMLString = noHTMLString.replaceAll("\r", "");

		// Remove New line from java string and replace html break
		noHTMLString = noHTMLString.replaceAll("\n", " ");
		noHTMLString = noHTMLString.replaceAll("\'", "&#39;");
		noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
		return noHTMLString;
	}

	/**
	 * 查看一个字符串 是否包含另外一个字符
	 * 
	 * @param a
	 *            如果a包含b 则显示a 否则显示a+b
	 * @param b
	 * @return
	 */
	public static String IsIndexOf(String a, String b) {
		if (a != null && b != null) {
			if (a.indexOf(b) != -1) {
				return a;
			} else {
				return a + b;
			}
		} else {
			return a;
		}

	}

	/**
	 * 判断字符是否为时间类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		Pattern pattern = Pattern
				.compile("^([0-9]{4})((0([1-9]{1}))|(1[0-2]))(([0-2]([0-9]{1}))|(3[0|1]))(([0-1]([0-9]{1}))|(2[0-4]))([0-5]([0-9]{1}))([0-5]([0-9]{1}))");
		Matcher matcher = pattern.matcher(str);
		boolean bool = matcher.matches();
		return bool;
	}

	@SuppressWarnings("rawtypes")
	public static String getCollectionStringBySplit(Collection collection, String split) {
		return getArrayStringBySplit(collection.toArray(), split);
	}

	public static String getArrayStringBySplit(Object[] collection, String split) {
		StringBuffer buf = new StringBuffer();
		if (split == null||split.length()==0) {
			split = ",";
		}
		for (Object obj : collection) {
			buf.append(obj).append(split);
		}
		if (buf.toString().endsWith(split))
			return buf.substring(0, buf.length() - 1);
		return buf.toString();
	}
	/**
	 * 去掉String数组中重复，或空的字符串
	 * @param strs
	 * @return
	 */
	public static String[] distinctStringArray(String[] strs) {
		Set<String> strList = new HashSet<String>();
		for (String str : strs) {
			if (!isEmpty(str))
				strList.add(str.trim());
		}
		return strList.toArray(new String [0]);
	}
	
	/**
	 * 是否包含中文字符
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean isContainCNChar(String s) throws UnsupportedEncodingException {
		Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher matcher=pattern.matcher(new String(s.getBytes(),"UTF-8"));  
		//matcher.find()或者matcher.lookingAt()
		return matcher.find();
	}
	
	public static String randomString(int size) {// 随机字符串
        char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q',
                'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
        Random random = new Random(); // 初始化随机数产生器
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            sb.append(c[Math.abs(random.nextInt()) % c.length]);
        }
        return sb.toString();
    }
	
	public static String getStackTrace(Throwable t){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
