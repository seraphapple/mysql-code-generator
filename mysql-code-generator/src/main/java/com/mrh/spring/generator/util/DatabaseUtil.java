package com.mrh.spring.generator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mrh.spring.generator.model.Column;
import com.mrh.spring.generator.model.Table;

public class DatabaseUtil {

	/**
	 * 用main方法直接跑的话需要提前加载驱动类
	 */
	public static void init() {
		 try {
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
		 } catch (InstantiationException e) {
			 e.printStackTrace();
		 } catch (IllegalAccessException e) {
			 e.printStackTrace();
		 } catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 }
	}
	
	/**
	 * 获取连接
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static Connection getConnection(String url, String username, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 操作结束后需要关闭连接
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取表结构信息并存在table里
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static Table getTableColumns(Connection conn, String tableName){
		DatabaseMetaData metaData;
		Table table = null;
		try {
			metaData = conn.getMetaData();
			ResultSet rs = metaData.getColumns(null, "%", tableName, "%");
			table = new Table();
			table.setTableName(tableName);
			List<Column> columns = new ArrayList<Column>();
			while(rs.next()) {
				 Column column = new Column();
				 column.setColumnName(rs.getString("COLUMN_NAME"));
				 column.setColumnType(rs.getString("TYPE_NAME"));
				 column.setReference(rs.getString("REMARKS"));
				 columns.add(column);
			}
			table.setColumns(columns);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}
}
