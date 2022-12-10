package com.aca.mealcraft.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class MariaDbUtil {

	private static String connectionUrl = 
			"jdbc:mariadb://localhost:3306/mealcraft_resources?user=root&password=*****************";
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUrl);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
	
//	public static void main(String[] args) {
//		
//		Connection connection = getConnection();
//		
//		if (null == connection) {
//			System.out.println("Help. Connection is null.");
//		} else {
//			System.out.println("A real connection!!!");
//		}
//		
//	}
	
}
