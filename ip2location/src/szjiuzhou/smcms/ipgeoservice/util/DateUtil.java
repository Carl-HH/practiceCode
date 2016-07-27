package szjiuzhou.smcms.ipgeoservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理类.
 * @author chen.hua
 * @since
 * @param
 */
public class DateUtil {
    /**
	 * 获取当前的时间.
	 * @author chen.hua
	 * @create 2015年11月10日 下午8:07:07
	 * @since
	 * @param
	 * @return String;
	 */
	public static String getNowDatetime(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = dateFormat.format(date);
		return now;
	}
}
