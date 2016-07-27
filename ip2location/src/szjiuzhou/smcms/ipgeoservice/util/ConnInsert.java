package szjiuzhou.smcms.ipgeoservice.util;

import java.sql.Connection;



import java.sql.PreparedStatement;

import szjiuzhou.smcms.ipgeoservice.domain.LocationBean;

/**
 * 插入数据.
 * @author chen.hua
 * @since
 * @param
 */
public class ConnInsert {
    String ip;
	String mac;
	String nowtime;
	LocationBean locationBean;
    public ConnInsert(String ip,String mac,String nowtime,LocationBean locationBean) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.mac = mac;
		this.nowtime = nowtime;
		this.locationBean = locationBean;
	}

	/**
	 * 将ip定位信息插入数据库.
	 * @author chen.hua
	 * @create 2015年11月13日 下午6:04:55
	 * @since
	 * @param
	 * @return
	 */
	public void insert(){
		ConnDb connDb = new ConnDb();
		Connection conn=null;
	    PreparedStatement stmt=null;
        String sql = "insert into `iplocation`(`ipaddr`,`macaddr`,`countrycode`,`country`,`region`,`city`,`latitude`,`longitude`,`time`) values (?,?,?,?,?,?,?,?,?)";
	      try {
				conn = connDb.getConnection();
		        stmt = conn.prepareStatement(sql);
		        stmt.setString(1, ip);
		        stmt.setString(2, mac);
		        stmt.setString(3, locationBean.getCountry_code());
		        stmt.setString(4, locationBean.getCountry_name());
		        stmt.setString(5, locationBean.getRegion_name());
		        stmt.setString(6, locationBean.getCity_name());
		        stmt.setString(7, locationBean.getLatitude());
		        stmt.setString(8, locationBean.getLongitude());
		        stmt.setString(9, nowtime);
		        stmt.execute();
			} catch(Exception e) {
				System.out.println("e:execute(String sqlstr)"+e.getMessage() + stmt.toString());
		    }
			finally {
				if (stmt != null) {
					try {
						stmt.close();				
					} catch(Exception ex) {
						System.out.println("ex:execute(String sqlstr)->conn.close() err"+ex.getMessage() + stmt.toString());
					}
				}
				connDb.getPool().returnConnection(conn);
			}
		
		 
		}
	}

