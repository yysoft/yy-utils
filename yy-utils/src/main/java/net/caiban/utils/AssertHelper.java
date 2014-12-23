/**
 * 
 */
package net.caiban.utils;

/**
 * @author mays
 *
 */
public class AssertHelper {

	/**
	 * 正整数(Integer)
	 * @param number
	 * @return
	 */
	public static boolean positiveInt(Integer number){
		if(number!=null && number.intValue()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 正整数(Long)
	 * @param number
	 * @return
	 */
	public static boolean positiveLong(Long number){
		if(number!=null && number.longValue()>0){
			return true;
		}
		return false;
	}
}
