package net.caiban.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 格式定义如下： y 年号，如 1996 M 月份，如 July 或者 07 d 月中第几天，如 12 H 小时(24制)，如 0、17 m 分钟,如 32 s 钞钟，如55 S 微钞，如978 E 星期几，如 Tuesday D
 * 一年中的第几天，如 189 w week in year (Number) 27 W week in month (Number) 2 a am/pm marker (Text) PM k hour in day (1~24)
 * (Number) 24 K hour in am/pm (0~11) (Number) 0 z time zone (Text) Pacific Standard Time ' escape for text (Delimiter)
 * '' single quote (Literal) '
 * 
 * @throws ParseException
 *             用法： DateUtil.getTimeStrByLongTime(1000L * 60L * 60L * 25L * 30L * 12L) 1年15天 <br>
 *             DateUtil.toString(new Date(),"yyyy年MM月dd日HH时mm分ss秒") 2007年12月27日10时49分52秒 <br>
 *             DateUtil.toString(DateUtil.getDate(new Date(),"yyyy-MM-DD"),"yyyy年MM月dd日HH时mm分ss秒") 2007年12月27日00时00分00秒 <br>
 *             DateUtil.toString(DateUtil.getDate("2005-1-1","yyyy-MM-DD" ),"yyyy年MM月dd日HH时mm分ss秒") 2005年01月01日00时00分00秒 <br>
 *             DateUtil.toString(DateUtil.getDateAfterDays(new Date(),-10),"yyyy年MM月dd日HH时mm分ss秒") 2007年12月17日10时49分52秒 <br>
 *             DateUtil.toString(DateUtil.getDateAfterMonths(new Date(),-2),"yyyy年MM月dd日HH时mm分ss秒") 2007年10月27日10时49分52秒 <br>
 *             DateUtil.toString(new Date(12345222),"yyyy年MM月dd日HH时mm分ss秒") 1970年01月01日11时25分45秒 <br>
 */
public class DateUtil {

	/**
	 * desc：Date转换为String
	 * 
	 * @param date
	 *            需要转换的date
	 * @param format
	 *            格式字符串
	 * @return 返回按照格式转化后的字符串 DateUtil.toString(new Date(),"yyyy-MM-dd HH:mm:ss SSS")
	 */
	public static String toString(Date date, String format) {
		if (date != null) {
			SimpleDateFormat dateformatter = new SimpleDateFormat(format);
			String dateString = dateformatter.format(date);
			return dateString;
		} else {
			return null;
		}
	}

	/**
	 * desc：Date时间转换为格式化后的Date时间
	 * 
	 * @param date
	 *            需要转换的date
	 * @param format
	 *            格式字符串
	 * @return 返回按照格式转化后的Date 如：DateUtil.getDate(new Date(),"yyyy-MM-dd")
	 */
	public static Date getDate(Date date, String format) throws ParseException {
		if (date == null)
			return null;
		SimpleDateFormat dateformatter = new SimpleDateFormat(format);
		return dateformatter.parse(dateformatter.format(date));
	}

	/**
	 * desc：String时间转换为格式化Date时间
	 * 
	 * @param date
	 *            需要转换的String
	 * @param format
	 *            格式字符串
	 * @return 返回按照格式转化后的Date 如：DateUtil.getDate("2007-11-01","yyyy-MM-dd")
	 */
	public static Date getDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat(format);
		return dateformatter.parse(dateStr);
	}

	/**
	 * desc：String时间转换为格式化Date时间
	 * 
	 * @param date
	 *            需要转换的String
	 * @param format
	 *            格式字符串
	 * @param locale
	 *            特定地区
	 * @return 返回按照格式转化后的Date 如：DateUtil.getDate("2007-11-01","yyyy-MM-dd",Locale.ENGLISH)
	 */
	public static Date getDate(String dateStr, String format, Locale locale) throws ParseException {
		SimpleDateFormat dateformatter = new SimpleDateFormat(format, locale);

		return dateformatter.parse(dateStr);
	}

	/**
	 * desc：获得距今天一定时间后的时间
	 * 
	 * @param date
	 *            转换前的时间
	 * @param field
	 *            增减单位
	 * @param add_days
	 *            增减数量
	 * @return 返回增减单位后的Date 如：DateUtil.getDateAfter(new Date(),Calendar.MONTH,-2) 2个月前的时间
	 */
	public static Date getDateAfter(Date date, int field, int add_days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, add_days);
		return calendar.getTime();
	}

	/**
	 * desc：获得格式化时间，时间为距几天天数
	 * 
	 * @param date
	 *            转换前的时间
	 * @param add_days
	 *            天数
	 * @return 返回增加天数之后的Date 如：DateUtil.getDateAfterDays(new Date(),-1) 一天前的时间
	 */
	public static Date getDateAfterDays(Date date, int add_days) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.DATE, add_days);
		return time.getTime();
	}

	/**
	 * desc：获得格式化时间，时间为距今天月数
	 * 
	 * @param date
	 *            转换前的时间
	 * @param add_months
	 *            月数
	 * @return 返回增减月数后的Date 如：DateUtil.getDateAfterMonths(new Date(),-2) 2个月前的时间
	 */
	public static Date getDateAfterMonths(Date date, int add_months) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.MONTH, add_months);
		return time.getTime();
	}

	/**
	 * desc：获得用时时间，12000=12秒
	 * 
	 * @param time_misec
	 *            毫秒数
	 * @return 返回用时时间字符串 如：DateUtil.getTimeStrByLongTime(12010) 12秒10毫秒
	 */
	public static String getTimeStrByLongTime(long time_misec) {
		StringBuffer bu = new StringBuffer(20);
		long parent = time_misec;
		long leav = parent % 1000;

		if (leav != 0) {
			bu.insert(0, leav + "毫秒");
		}

		long child = parent / 1000;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 60;

		if (leav != 0) {
			bu.insert(0, leav + "秒");
		}

		child = child / 60;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 60;

		if (leav != 0) {
			bu.insert(0, leav + "分钟");
		}

		child = child / 60;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 24;

		if (leav != 0) {
			bu.insert(0, leav + "小时");
		}

		child = child / 24;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 30;

		if (leav != 0) {
			bu.insert(0, leav + "天");
		}

		child = child / 30;

		if (child == 0) {
			return bu.toString();
		}

		leav = child % 12;

		if (leav != 0) {
			bu.insert(0, leav + "月");
		}

		child = child / 12;

		if (child == 0) {
			return bu.toString();
		}

		bu.insert(0, child + "年");

		return bu.toString();
	}

	/**
	 * desc：获取相距时间time参数为年月日时分秒的时间
	 * 
	 * @param int...time 年月日时分秒顺序输入参数,允许部分参数
	 * @return 返回时间 如：DateUtil.getTimeAfterTime(2007,12,26)
	 */
	public static Date getTimeAfterTime(int... time) {
		Calendar now = new GregorianCalendar();

		if ((time == null) || (time.length == 0)) {
			return now.getTime();
		}

		int len = time.length;
		int[] times = time;

		for (int i = 0; i < len; i++) {
			switch (i) {
				case 0:
					now.add(Calendar.YEAR, times[i]);
					break;
				case 1:
					now.add(Calendar.MONTH, times[i]);
					break;
				case 2:
					now.add(Calendar.DATE, times[i]);
					break;
				case 3:
					now.add(Calendar.HOUR, times[i]);
					break;
				case 4:
					now.add(Calendar.MINUTE, times[i]);
				case 5:
					now.add(Calendar.SECOND, times[i]);
					break;
			}
		}
		return now.getTime();
	}

	/**
	 * desc：获取上月第一天的时间
	 * 
	 * @return 返回上月第一天的时间 如：DateUtil.getLastMonthFistDay()
	 */
	public static Date getLastMonthFistDay() {
		Calendar now = new GregorianCalendar();
		now.add(Calendar.MONTH, -1);
		Calendar stdCal = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
		return stdCal.getTime();
	}

	/**
	 * desc：获取本月第一天的时间
	 * 
	 * @return 返回本月第一天的时间 如：DateUtil.getNowMonthFirstDay()
	 */
	public static Date getNowMonthFirstDay() {
		Calendar now = new GregorianCalendar();
		int curYear = now.get(Calendar.YEAR);
		int curMonth = now.get(Calendar.MONTH);
		Calendar stdCal = new GregorianCalendar(curYear, curMonth, 1);
		return stdCal.getTime();
	}

	/**
	 * @return 精确到秒的时间截,long格式
	 */
	public static long getSecTimeMillis() {
		return System.currentTimeMillis() / 1000l;
	}

	/**
	 * @param day
	 *            与当前日期的天数差,+X表示后面X天,-X表示前面X天,0表示今天
	 * @return 返回与今天相差day天的零点的时间截,精确到秒
	 */
	public static long getTheDayZero(int day) {
		Calendar calendar = Calendar.getInstance(); // 当前日期
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime().getTime() / 1000l;
	}
	
	public static long getTheDayZero(Date day,int intervalDays) {
		Calendar calendar = Calendar.getInstance(); // 当前日期
		calendar.setTime(day);
		calendar.add(Calendar.DATE, intervalDays);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime().getTime() / 1000l;
	}
	
	//获取指定日期的年份
	public static int getYearForDate(Date day) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(day);
		return calendar.get(Calendar.YEAR);
	}
	//获取指定日期的月份 从1开始，默认为0
	public static int getMonthForDate(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		return calendar.get(Calendar.MONTH)+1;
	}
	//获取指定日期在月份中的天序列
	public static int getDayOfMonthForDate(Date day) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(day);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	//获取指定日期的星期序列（星期日开始）
	public static int getDayOfWeekForDate(Date day) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(day);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * @param date
	 * @return 返回指定日期时间的时间截 如：DateUtil.getMillis(new Date());
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	public static void main(String[] args) throws ParseException {
		//		 String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
		//		 // if no ids were returned, something is wrong. get out.
		//		 if (ids.length == 0)
		//		     System.exit(0);
		//
		//		  // begin output
		//		 System.out.println("Current Time");
		//
		//		 // create a Pacific Standard Time time zone
		//		 SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
		//
		//		 // set up rules for daylight savings time
		//		 pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		//		 pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		//
		//		 // create a GregorianCalendar with the Pacific Daylight time zone
		//		 // and the current date and time
		Calendar calendar = new GregorianCalendar();
		System.out.println(Calendar.getInstance().getClass().getName());
		System.out.println(calendar.getClass().getName());

		Date trialTime = getDate("2011-01-01", "yyyy-MM-dd");
		calendar.setTime(trialTime);

		// print out a bunch of interesting things
		System.out.println("ERA: " + calendar.get(Calendar.ERA));
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
		System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("DATE: " + calendar.get(Calendar.DATE));
		System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println(toString(trialTime, "yyyy-MM-dd"));
		//calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK) + 1);
		System.out.println(toString(calendar.getTime(), "yyyy-MM-dd"));
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));

		System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
		System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
		System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
		System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
		System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
		System.out.println("ZONE_OFFSET: "
				+ (calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000)));
		System.out.println("DST_OFFSET: " + (calendar.get(Calendar.DST_OFFSET) / (60 * 60 * 1000)));

		System.out.println("Current Time, with hour reset to 3");
		calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
		calendar.set(Calendar.HOUR, 3);
		System.out.println("ERA: " + calendar.get(Calendar.ERA));
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
		System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("DATE: " + calendar.get(Calendar.DATE));
		System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
		System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
		System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
		System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
		System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
		System.out.println("ZONE_OFFSET: "
				+ (calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000))); // in hours
		System.out.println("DST_OFFSET: " + (calendar.get(Calendar.DST_OFFSET) / (60 * 60 * 1000))); // in hours
		
		System.out.println(toString(new Date(getTheDayZero(new Date(),0)*1000),"yyyy-MM-dd HH:mm:ss"));
		
		//System.out.println(DateUtil.getIntervalDays(DateUtil.getDate("2014-02-14", "yyyy-MM-dd"), DateUtil.getDate("2012-11-13", "yyyy-MM-dd")));
	}

	/**
	 * 判断是否润年
	 * 
	 * <pre>
	 * 1.被400整除是闰年 
	 * 2.能被4整除同时不能被100整除则是闰年
	 * </pre>
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(Integer year) {
		return (year % 400) == 0 || ((year % 4) == 0 && (year % 100) != 0);
	}

	/**
	 * 比较两个日期相差的天数（非严格意义上的天，忽略小时和分）
	 * 
	 * @param fDate
	 *            较新的日期
	 * @param oDate
	 *            较老的日期
	 * @return
	 * @throws ParseException
	 */
	public static int getIntervalDays(Date fDate, Date oDate) throws ParseException {
		String fDate1 = toString(fDate, "yyyyMMdd");
		String oDate1 = toString(oDate, "yyyyMMdd");
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyyMMdd");
		java.util.Date cDate = df.parse(fDate1);
		java.util.Date dDate = df.parse(oDate1);
		return (int) ((cDate.getTime() - dDate.getTime()) / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * 得到本周第一天
	 * 
	 * @return date
	 */
	public static Date getFirstDateThisWeek() {
		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		return cal.getTime();
	}

	/**
	 * 得到本周最后一天
	 * 
	 * @return date
	 */
	public static Date getLastDateThisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 6);
		return cal.getTime();
	}

	public static int getIntervalMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}
	
	public static Date getMondayFirstOfWeek(Date baseDate){
		int dayofweek = getDayOfWeekForDate(baseDate);
		dayofweek=dayofweek==1?dayofweek+7:dayofweek;
		return getDateAfterDays(baseDate, 2-dayofweek);
	}
}
