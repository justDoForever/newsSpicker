package com.whch.newspicker.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC的工具类：
 * @author admin
 */
public class JDBCUtils {
	private static final ComboPooledDataSource DATA_SOURCE =new ComboPooledDataSource();
	/**
	 * 获得连接的方法
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DATA_SOURCE.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static DataSource getDataSource(){
		return DATA_SOURCE;
	}
	
}
