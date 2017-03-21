package com.svgteam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	protected Connection conn;
	protected PreparedStatement ps;
	protected ResultSet rs;
	private String url;
	private String name;
	private String pwd;
	public void init(String dataBaseName){
		this.url = "jdbc:mysql://localhost:3306/"+dataBaseName;
		this.name="root";
		this.pwd="root";
	}
	public void init(String dataBaseName,String name,String pwd){
		this.url = "jdbc:mysql://localhost:3306/"+dataBaseName;
		this.name=name;
		this.pwd= pwd;
	}

	public void open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, name, pwd);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


