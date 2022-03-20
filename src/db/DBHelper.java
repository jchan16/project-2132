package db;

import java.sql.Connection;
import java.sql.SQLException; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public abstract class DBHelper {
	protected final String URL ="jdbc:postgresql://ec2-54-226-18-238.compute-1.amazonaws.com:5432/d61guaj1ej2jrv";
	protected final String User = "kjjzwyumxfhfah";
	protected final String Password = "72c34744cb8ab0da6b19169aee323d6948ea8991aab8a8c82c250de2d371e646";
	
	protected Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(URL,User,Password);
		return connection;
	}
}
