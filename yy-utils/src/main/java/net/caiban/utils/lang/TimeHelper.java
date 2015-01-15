/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-25
 */
package net.caiban.utils.lang;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-25
 */
public class TimeHelper {

	public static String formatTime(long time){
		StringBuffer timesb = new StringBuffer();
		
		long day=time/86400000;
		if(day>0){
			timesb.append(day).append("天");
			time=time-86400000;
		}
		
		long hour=time/(60*60*1000);
		if(hour>0){
			timesb.append(hour).append("小时");
			time=time-(60*60*1000*hour);
		}else{
			if(timesb.length()>0){
				timesb.append(hour).append("小时");
			}
		}
		
		long m=time/(60*1000);
		if(m>0){
			timesb.append(m).append("分");
			time=time-(60*1000*m);
		}else{
			if(timesb.length()>0){
				timesb.append(m).append("分");
			}
		}
		
		long s=time/1000;
		if(s>0){
			timesb.append(s).append("秒");
			time=time-(1000*s);
		}else{
			if(timesb.length()>0){
				timesb.append(s).append("秒");
			}
		}
		
		timesb.append(time).append("毫秒");
		return timesb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(TimeHelper.formatTime(999));
		System.out.println(TimeHelper.formatTime(25210));
		System.out.println(TimeHelper.formatTime(3502020));
		System.out.println(TimeHelper.formatTime(7260000));
		System.out.println(TimeHelper.formatTime(86400000));
		System.out.println(TimeHelper.formatTime(86468000));
	}
}
