package szjiuzhou.smcms.ipgeoservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mysql.jdbc.PreparedStatement;

import szjiuzhou.smcms.ipgeoservice.domain.LocationBean;
import szjiuzhou.smcms.ipgeoservice.util.ConnDb;
import szjiuzhou.smcms.ipgeoservice.util.ConnInsert;
import szjiuzhou.smcms.ipgeoservice.util.IPGEOUtil;
import szjiuzhou.smcms.ipgeoservice.util.IPorMacCheck;
import szjiuzhou.smcms.ipgeoservice.util.DateUtil;

/**
 * Servlet implementation class ip2location
 */
@WebServlet("/getlocation")
public class getlocation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getlocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ip = request.getParameter("ip");
		String mac = request.getParameter("mac");
		String result = "";
		PrintWriter out = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		if (IPorMacCheck.isIpAddress(ip) && IPorMacCheck.isMacAddress(mac)){
		try {
			String nowTime = DateUtil.getNowDatetime();

			ConnDb connDb = new ConnDb();
			
			ArrayList<Hashtable<String, String>> locationList = new ArrayList<Hashtable<String,String>>();
			locationList =  IPorMacCheck.getLocationByip(connDb, ip);
			
			if(!locationList.isEmpty()){
				System.out.println("locationList:"+locationList.get(0).get("city"));
				result = IPGEOUtil.setListToJson(locationList);
			}
			else{
				System.out.println("get the iploaction from ipwebapi");
				try {
				    result = IPGEOUtil.getLocation(ip);
				    LocationBean locationBean = objectMapper.readValue(result,LocationBean.class);
				    System.out.println("locationbean:"+locationBean.getCity_name());
				 /*   String sql = "insert into `iplocation`(`ipaddr`,`macaddr`,`countrycode`,`country`,`region`,`city`,`latitude`,`longitude`,`time`) values ( "+
				    "'"+ip+"',\'"+mac+"',\'"+locationBean.getCountry_code()+"',\'"+locationBean.getCountry_name()+"',\'"+locationBean.getRegion_name()+"',\'"+locationBean.getCity_name() +
				    "',\'" +locationBean.getLatitude()+"',\'"+locationBean.getLongitude()+"',\'"+nowTime +"')";
				    */
				    
				    ConnInsert insertDb = new ConnInsert(ip, mac, nowTime, locationBean);
				    insertDb.insert();
				    
				} catch (Exception e) {
					result = "could not get the location";
					e.printStackTrace();
				}
			}
			
		}catch(Exception e){
			result = "could not get the location";
			e.printStackTrace();
			
		} finally {
			
		}
		}else {
			result = "the ip or mac is not corrrect";
		}
		out.write(result);
/*		if (IPorMacCheck.isIpAddress(ip) && IPorMacCheck.isMacAddress(mac)) {
			try {
				String result = IPGEOUtil.getLocation(ip);
				out.write(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			out.write("the ip or mac is not corrrect");
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/*
	 * public static String checkip(final String ip){
	 * 
	 * return null; }
	 * 
	 * public static String checkMac(final String mac){
	 * 
	 * return null; }
	 */

}
