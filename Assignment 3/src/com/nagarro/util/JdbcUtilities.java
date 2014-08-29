package com.nagarro.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.nagarro.datastore.DatabaseConstants;

public class JdbcUtilities {

	private static Connection connection;
	static {
		try {
			Class.forName(DatabaseConstants.JDBC_DRIVER);
			// Open a connection
			connection = DriverManager.getConnection(DatabaseConstants.DB_URL,
					DatabaseConstants.USER, DatabaseConstants.PASS);

		} catch (ClassNotFoundException | SQLException exception) {
			exception.printStackTrace();
		}
	}

	private JdbcUtilities() {

	}

	public static Connection getConnection() {
		return connection;
	}
}
