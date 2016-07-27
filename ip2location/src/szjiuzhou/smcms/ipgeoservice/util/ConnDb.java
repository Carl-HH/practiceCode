
/***********************************************************
 * Title:			公共模板
 * Description:		这是一个关于数据库有关操作的类
 * Copyright:		Copyright (c) 2012
 * Company:			szjiuzhou
 * @author			王齐平
 * @date            2012/3/5
 * @version 1.0		
 ***********************************************************/

package szjiuzhou.smcms.ipgeoservice.util;

//import javax.naming.*;
import java.sql.*;
//import javax.sql.DataSource;
//import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class ConnDb{
	
	private ConnDbPool pool = ConnDbPool.getInstance();
	
	public ConnDb(){

	}
	
	
/*	public String getDbFilePath(){
		return pool.getDbFilePath();
	}*/
	
	/**
	 * @return the pool
	 */
	public ConnDbPool getPool() {
		return pool;
	}

	/** 

   * 获取连接池中的数据库一个连接
   * 
	 * @return  数据库连接
	 */    
  public Connection getConnection() {
	//从连接池返回一个数据库连接
      //Connection conn = null;
		try {
		
		/*Context initCtx=new InitialContext();//tomcat
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/TestDB");
		Connection conn=ds.getConnection();*/
			/*DataSource pool;
			Context env = (Context) new InitialContext().lookup("java:comp/env");//resin
			pool = (DataSource) env.lookup("jdbc/test");
			conn = pool.getConnection();*/
			/*String driver="com.mysql.jdbc.Driver";//"com.microsoft.jdbc.sqlserver.SQLServerDriver";
			String url="jdbc:mysql://127.0.0.1:3306/ads?user=root&password=sa&useUnicode=true&characterEncoding=gb2312&autoReconnect=true&failOverReadOnly=false";//"jdbc:microsoft:sqlserver://192.168.16.16:1433;DatabaseName=ads";
			
			Class.forName(driver);
			conn=DriverManager.getConnection(url,"root","sa");*/
			return pool.getConnection();
		}
		catch (SQLException e) {
			System.out.println(e);
			return null;
		}
  }

	/** 

   * 释放数据库一个连接到连接池中
   * 
	 * @return  void
	 */    
  public void releaseConnection(Connection conn) {
  	//从连接池返回一个数据库连接
		this.pool.returnConnection(conn);
  }	
	
  public PreparedStatement prepareStatement(String sql) throws SQLException{
	 return (PreparedStatement) this.getConnection().prepareStatement(sql);
  }
  
	/** 

   * 执行数据更新,如Update,Insert into,Delete，无事物控制
   * 
	 * @return  执行成功返回1否则返回0
	 */    
	public int executeUpdate(String sqlstr){
      Connection conn=null;
	    Statement stmt=null;
		int ret = 0;
      try {
          conn = getConnection();
	        stmt = conn.createStatement();
	        stmt.executeUpdate(sqlstr);
			ret = 1;
	    } catch(Exception e) {
			System.out.println("e:executeUpdate(String sqlstr)"+e +sqlstr);
	    }
		
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.returnConnection(conn);
		}
		return ret;
  }

	/** 

   * 执行数据更新,如Update,Insert into,Delete，有事物控制
   * 
	 * @return  执行成功返回1否则返回0
	 */    
	public int executeUpdate(String []sqlstr){
	/***************************************
	*执行数据更新,如Update,Insert into,Delete
	*执行成功返回1否则返回0
	***************************************/
      Connection conn=null;
	    Statement stmt=null;
		int ret = 0;
		int i = 0;
		
      try{
          conn = getConnection();
			
			//禁止自动提交，设置回滚点 
			conn.setAutoCommit(false);
	        stmt = conn.createStatement();

			for(i=0; i< sqlstr.length; i++)
			{
				if (sqlstr[i] == null || sqlstr[i].equals(""))
				{
					continue;
				}
	        	stmt.executeUpdate(sqlstr[i]);
			}
			
			//事务提交
			conn.commit(); 
			ret = 1;
	    }catch(Exception e){
			System.out.println("e:executeUpdate(String sqlstr)"+e +sqlstr[i]);
			try {    
				//操作不成功则回退    
				conn.rollback();    
			} catch(Exception e1) {    
				e1.printStackTrace();    
			} 			
	    }
		finally{
			if (stmt != null) {
				try{
					stmt.close();
				}catch(Exception ex){
					System.out.println("ex:conn.close() err="+ex);
				}
			}
			pool.returnConnection(conn);
		}
		return ret;
  }

	/** 

   * 执行数据更新,如Update,Insert into,Delete，有事物控制
   * 
	 * @return  执行成功返回1否则返回0
	 */    
	public int executeUpdate(String []sqlstr,String []sql){
      Connection conn=null;
	    Statement stmt=null;
		int ret = 0;
		int i = 0;
		
      try{
          conn = getConnection();
			
			//禁止自动提交，设置回滚点 
			conn.setAutoCommit(false);
	        stmt = conn.createStatement();

			for(i=0; sqlstr!=null && i< sqlstr.length; i++)
			{
				if ( sqlstr[i] != null || sqlstr[i].equals(""))
				{
					continue;
				}
	        	stmt.executeUpdate(sqlstr[i]);
			}

			for(i=0; sql!=null &&  i< sql.length; i++)
			{
				if ( sql[i] != null || sql[i].equals(""))
				{
					continue;
				}
	        	stmt.executeUpdate(sql[i]);
			}
			
			//事务提交
			conn.commit(); 
			ret = 1;
	    }catch(Exception e){
			try {    
				//操作不成功则回退    
				conn.rollback();    
			}catch(Exception e1){    
				e1.printStackTrace();    
			} 			
			System.out.println("e:executeUpdate(String sqlstr)"+e +sqlstr[i]);
	    }
		finally{
			if(stmt!=null){
				try{
					stmt.close();
				}catch(Exception ex){
					System.out.println("ex:conn.close() err="+ex);
				}
			}
			pool.returnConnection(conn);
		}
		return ret;
  }


	/** 

   * 执行SQL语句
   * 
	 * @return  void
	 */    
	public void execute(String sqlstr){
		Connection conn=null;
  	Statement stmt=null;
      try {
			conn = getConnection();
	        stmt = conn.createStatement();
 	        stmt.execute(sqlstr);
		} catch(Exception e) {
			System.out.println("e:execute(String sqlstr)"+e.getMessage() + sqlstr);
	    }
		finally {
			if (stmt!=null) {
				try {
					stmt.close();				
				} catch(Exception ex) {
					System.out.println("ex:execute(String sqlstr)->conn.close() err"+ex.getMessage() + sqlstr);
				}
			}
			pool.returnConnection(conn);
		}
	}

	/** 

   * 返回结果集的总字段数, 如返回-1则说明出现错误
   * 
	 * @return  记录数
	 */    
	public int executeQueryNum(String sql){
      Connection conn = getConnection();
      Statement stmt = null;
		ResultSet rs = null;
		int queryNum = -1;
      try {
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          ResultSetMetaData rsmd = rs.getMetaData();
          queryNum = rsmd.getColumnCount();            
      } catch(Exception e) {
			System.out.println("e: executeQueryNum(String sql)"+e.getMessage()+sql);
      } finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt!=null) {
				try{
					stmt.close();
				}catch(Exception ex){
					System.out.println("ex: executeQueryNum(String sql)"+ex.getMessage()+sql);
				}
			}
			pool.returnConnection(conn);
		}
		return queryNum;
	}

	/** 

   * 根据形如“select count(*) from ...”sql语句返回结果集的总数,如返回-1则说明出现错误
   * 
	 * @return  记录数
	 */    
	public int executeCounts(String sql){
      Connection conn = getConnection();
      Statement stmt = null;
		ResultSet rs = null;
		int queryNum = -1;
      try{
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          if (rs != null){
              rs.next();
              queryNum = rs.getInt(1);
          }
      }catch(Exception e){
			System.out.println("e:executeCounts(String sql)"+e.getMessage()+sql);
      }
		finally{

			if(rs!=null){
	            try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if(stmt!=null){
				try{
					stmt.close();
	            }catch(Exception ex){
					System.out.println("ex:executeCounts(String sql)"+ex.getMessage()+sql);
		        }
			}
			pool.returnConnection(conn);
		}
		return queryNum;
  }
	
	
	/** 
	 针对存在一条结果集的情况下取某字段的值。
   * 根据形如“select xxx from ...”sql语句返回xxx字段属性,如返回-1则说明出现错误
   * 
	 * @return  记录数
	 */  
	
	public String executeGetString(String sql){
      Connection conn = getConnection();
      Statement stmt = null;
		ResultSet rs = null;
		String str[] = new String[1];
      try{
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          if (rs != null){
              rs.next();
              str[0] = rs.getString(1);
              
          }
      }catch(Exception e){
			System.out.println("executeGetString(String sql)"+e.getMessage()+sql);
      }
		finally{

			if(rs!=null){
	            try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if(stmt!=null){
				try{
					stmt.close();
	            }catch(Exception ex){
					System.out.println("ex:executeCounts(String sql)"+ex.getMessage()+sql);
		        }
			}
			pool.returnConnection(conn);
		}
      
   return str[0];
  }
	/** 

   * 从结果集中取出一条记录
   * 
	 * @return  记录
	 */    
	 private Hashtable<String, String> getTheValue(ResultSet rs, ResultSetMetaData rsmd){
		Hashtable<String, String> hstab = new Hashtable<String, String>();
	    try{
          if(rs != null){
              int maxrows = rsmd.getColumnCount();
              for (int i = 1; i <= maxrows; i++){
                  String key = rsmd.getColumnName(i);
                  String value = rs.getString(i);
                  if (value == null){
                      value = "";
                  }
                  hstab.put(key, value);
              }
          }
      }catch(Exception e){
          System.out.println("e:getTheValue(String sql)"+e.getMessage());
      }
		return hstab;
  }

	/** 

   * 根据查询sqlString语句按pageSize对应页Page的结果集
   * 
	 * @return  结果集
	 */
  public ArrayList<Hashtable<String, String>> getList(String sqlstr,int page,int pageSize){
		ArrayList<Hashtable<String, String>> pkv = new ArrayList<Hashtable<String, String>>();
		ResultSet rs = null;
		Connection conn=null;
		Statement stmt=null;
		
		try{
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sqlstr);
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			int rowbegin = 0,j=0;
			rowbegin = (page-1)*pageSize+1;
			rs.absolute(rowbegin);

			if (rs.getRow() == 0){	
				return null;
			}
			while (j<pageSize){
				Hashtable<String, String> table = new Hashtable<String, String>();
				for (int i = 1;i <= num; i++){
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);
					if (value==null){
						value = "";
					}
					table.put(key,value);
				}
				pkv.add(table);

				if(!rs.next()){
					break;
				}
				j++;
			}
		}
		catch (SQLException e){
			System.out.println("e:getList(String sqlstr,int page,int pageSize)"+e+sqlstr);
		}
		finally
		{
			/*if(conn!=null)
			{
				try{
					conn.close();
				}catch(Exception ex){
					System.out.println("ex:getList(String sqlstr,int page,int pageSize)"+ex+sqlstr);
				}
			}*/
			try{
				
				if (rs != null) {
					rs.close();
				}
			} catch(Exception ex) {
				System.out.println("ex:getList(String sqlstr,int page,int pageSize)"+ex+sqlstr);
			}

			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception ex) {
				System.out.println("ex:getList(String sqlstr,int page,int pageSize)"+ex+sqlstr);
			}

			pool.returnConnection(conn);
		}
		
		return pkv;
		/*int rowbegin = (page-1)*pageSize;
		String sql = sqlstr + " limit " + rowbegin + "," + pageSize;
		return getList(sql);*/
	}


	/** 

   * 根据查询sqlString语句获取结果集
   * 
	 * @return  结果集
	 */
	public ArrayList<Hashtable<String, String>> getList(String sqlString){
	/*********查询结果集,返回ArrayList***********/
		ArrayList<Hashtable<String, String>> pkv = new ArrayList<Hashtable<String, String>>();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs = null;

		try{
		    conn = this.getConnection();
		    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    rs = stmt.executeQuery(sqlString);
		    ResultSetMetaData rsmd = rs.getMetaData();
 		    int num = rsmd.getColumnCount();
		    while (rs.next())
			{
			    Hashtable<String, String> table = new Hashtable<String, String>();
			    for (int i = 1;i <= num; i++)
				{
					String key = rsmd.getColumnName(i);
			    	String value = rs.getString(i);
			    	if (value==null)
					{
			    		value = "";
					}
					table.put(key,value);
			    }
			    pkv.add(table);
		    }
		}catch (SQLException e){
			//System.out.println(sqlString);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch(Exception ex) {
				//System.out.println(sqlString);
				ex.printStackTrace();
			}

			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception ex) {
				//System.out.println(sqlString);
				ex.printStackTrace();
			}
			pool.returnConnection(conn);
		}
		return pkv;
	}

	/** 

   * 获取一条记录 
   * 
	 * @return  记录
	 */
	public Hashtable<String, String> getValue(String sql){
      Hashtable<String, String> hstab = new Hashtable<String, String>();
      Connection conn = getConnection();
      Statement stmt = null;
		ResultSet rs = null;

      try
		{
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          ResultSetMetaData rsmd = rs.getMetaData();
          if (rs.next())
			{
              hstab = this.getTheValue(rs, rsmd);
          }
      }catch(Exception e){
			System.out.println(sql);
			e.printStackTrace();
      }
		
		finally
		{		
			try {
				if (rs != null) {
					rs.close();
				}
			} catch(Exception ex) {
				System.out.println(sql);
				ex.printStackTrace();
			}

			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception ex) {
				System.out.println(sql);
				ex.printStackTrace();
			}
			pool.returnConnection(conn);
		}
      return hstab;
  }

	/**
	*执行存储过程；
	*@param1 存储过程名字；
	*@param2 要给存储过程传入的值的数组；
	*@param3 存储过程的传出值的个数；
	*返回值为存储过程的返回值。
	*/
	public String[] execProcedure(String procedureName,String[] inParam,int outParamCount){
		Connection conn = getConnection();
		CallableStatement cstmt = null;			
		String strProcedure = "";
		String[] strReturn = null;
		int i;
		int nIn = 0;
		
		//传入参数的个数
		if(inParam!=null)
		{
			nIn = inParam.length;
		}
		
		//************生成执行存储过程语句************//
		for(i=0;i<nIn+outParamCount;i++)
		{
			strProcedure = strProcedure + ",?" ;
		}

		if(strProcedure.length()>0)
		{
			strProcedure = strProcedure.substring(1,strProcedure.length());
		}

		strProcedure="{call" + " " + procedureName + "(" + strProcedure + ")}";
		
		//*******************************************//
		try
		{
			cstmt=conn.prepareCall(strProcedure);
			
			//**********注册传入参数***************//
			for(i=0;i<nIn;i++)
			{
				cstmt.setString(i+1,inParam[i]);
			}
			//************************************//
			
			//************注册传出参数*************//
			for(i=nIn;i<nIn+outParamCount;i++)
			{
				cstmt.registerOutParameter(i+1,1);
			}
			//************************************//

			cstmt.executeUpdate();

			//****************返回值处理********************//
			if(outParamCount == 0)
			{
				strReturn = new String[2];
				strReturn[0] = "无返回值";
				strReturn[1] = "1";
			}
			else
			{
				strReturn = new String[outParamCount+1];
				for(i=nIn;i<nIn+outParamCount;i++)
				{
					strReturn[i-nIn]=cstmt.getString(i+1);
				}
				strReturn[outParamCount] = "1";
			}
			//*******************************************//
		}catch(Exception e){
			strReturn = new String[2];
			strReturn[0] = "存储过程执行错误："+e;
			strReturn[1] = "0";
		}
		
		//最后释放资源
		finally
		{
			if(conn!=null)
			{
				try
				{
					if (cstmt != null)
					{
						cstmt.close();
					}
					pool.returnConnection(conn);
				}catch(Exception sqle){
					strReturn = new String[2];
					strReturn[0] = "数据库错误："+sqle;
					strReturn[1] = "0";
				}
			}
		}
		return strReturn;
	}
	
	public String getValue(ConnDb db,String queryStr,String relt,String tableName,String id) {
		String sql = "select distinct " + relt + " from "+ tableName + " WHERE "+id+"='" + queryStr + "'";
		Hashtable<String, String> hst = db.getValue(sql);
		return (String) hst.get(relt);
	}
	
	//返回执行数量
	public int getCounts(String sql)
	{
		ResultSet rs = null;
		Connection conn=null;
		Statement stmt=null;
		int rowCount = 0;
		
		try{
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rs.last();
			rowCount = rs.getRow();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return rowCount;
	}

}

