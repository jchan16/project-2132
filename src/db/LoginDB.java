package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

public class LoginDB extends DBHelper{

	public int LoginPatient(String user, String pass) throws SQLException{
		int result = -1;
		Connection connection = super.getConnection();
			
		String query1 = "select patient_id from public.patient where username = ? and password = ?";
		PreparedStatement getPatient = connection.prepareStatement(query1);
			
		getPatient.setString(1, user);
		getPatient.setString(2,pass);
			
		ResultSet rs = getPatient.executeQuery();
			
		if(rs.next()) {
			result = rs.getInt(1);
		}
		
		connection.close();
		return result;		
	}
	
	public Object[] LoginEmployee(String user, String pass) throws SQLException{
		Object[] info = new Object[2];
		
		int result = -1;
		String role = "";
		Connection connection = super.getConnection();
			
		String query1 = "select employee_id,role from public.employee where username = ? and password = ?";
		PreparedStatement getPatient = connection.prepareStatement(query1);
			
		getPatient.setString(1, user);
		getPatient.setString(2,pass);
			
		ResultSet rs = getPatient.executeQuery();
			
		if(rs.next()) {
			result = rs.getInt(1);
			role = rs.getString(2);
		}
		
		info[0] = result;
		info[1] = role;
		
		connection.close();
		return info;	
	}

	public static void main(String[] args) throws SQLException{
		/*LoginDB db = new LoginDB();
		Object[] result = db.LoginEmployee("MS#", "MS1123456");
		System.out.println("DOne");*/
	}
	
}
