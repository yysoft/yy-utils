package net.caiban.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassHelper {
	public static Class<?> load(String jarpath, String className) throws ClassNotFoundException, MalformedURLException {
		URL url = new URL("file:" + jarpath);
		URLClassLoader myClassLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread().getContextClassLoader());
		return myClassLoader.loadClass(className);
	}
	public static  Class<?> load(  String className) throws ClassNotFoundException{
		return Class.forName(className);
	}
}
