package net.caiban.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5 {
	public final static int LENGTH_16 = 16;
	public final static int LENGTH_32 = 32;

	/**
	 * 计算字符串的MD5值，默认计算16位长度的MD5
	 */
	public static String encode(String s) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		return encode(s, LENGTH_16);
	}

	/**
	 * 计算字符串的MD5值
	 * @param s:待计算的字符串
	 * @param length:MD5长度,16或32
	 */
	public static String encode(String s, int length)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		StringBuffer hexValue = new StringBuffer();
		byte[] md5Bytes = md5.digest(s.getBytes("utf-8"));
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		if (length == 32) {
			return hexValue.toString();
		} else {
			return hexValue.toString().substring(8, 24);
		}
	}

	public static String[] autoMD5() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		String str[] = new String[2];
		// 随机生成八位整数
		Integer ranVal = Math.abs(new Random().nextInt()) % 100000000;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		StringBuffer hexValue = new StringBuffer();
		byte[] md5Bytes = md5.digest(ranVal.toString().getBytes("utf-8"));
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		str[0] = ranVal.toString();
		str[1] = hexValue.toString().substring(8, 24);
		return str;
	}

}
