package szjiuzhou.smcms.ipgeoservice.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPorMacCheck {

/**
 * 检验是否是合法的IP地址
 * @author chen.hua
 * @create 2015年11月6日 上午9:29:23
 * @since
 * @param address String IP地址
 * @return boolean IP地址是否合法
 */
public static boolean isIpAddress(String address){
	
		System.out.println("IPorMacCheck.isIpAddress()ip:"+address);

        String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        				
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(address);
        
        System.out.println("IPorMacCheck.isIpAddress()ip:"+m.matches());
        
        return m.matches();
    }

/**
 * 检验是否是合法的Mac地址
 * @author chen.hua
 * @create 2015年11月6日 上午9:49:56
 * @since
 * @param address String MAC地址
 * @return boolean MAC地址是否合法
 */
public static boolean isMacAddress(String address){
	
	System.out.println("IPorMacCheck.isMacAddress()mac:"+address);
	
	String regex = "^[A-F0-9]{2}(:[A-F0-9]{2}){5}$";
	
	 Pattern p = Pattern.compile(regex);
     Matcher m = p.matcher(address);
     
     System.out.println("IPorMacCheck.isIpAddress()mac:"+m.matches());

     return m.matches();
}

/**
 * 检查数据库中是否已有该ip地址信息.
 * @author chen.hua
 * @create 2015年11月11日 上午9:08:33
 * @since
 * @param
 * @return
 */
public static ArrayList<Hashtable<String, String>>  getLocationByip(ConnDb db,String ip){
	ArrayList<Hashtable<String, String>> location = new ArrayList<Hashtable<String,String>>();
	
	location = db.getList("select * from iplocation where ipaddr = "+"'"+ip+"'");
	
	
	return location;
}
}
