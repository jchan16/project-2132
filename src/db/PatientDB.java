package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import objects.Appointment;
import objects.Branch;
import objects.Dentist;

public class PatientDB extends DBHelper {
	
	public ArrayList<Appointment> queryAllAppointments(int id) throws SQLException{
		
		Connection connection = super.getConnection();
		
		String query = "select patient_id, appointment_date,start_time,end_time,dentist_id,branch_id,appointment_type, appointment_status, b.name, u.name "
				+ "from appointment as a "
				+ "join branch as b on (a.branch_id = b.branchid) "
				+ "join public.user as u on (a.dentist_id = u.id) "
				+ "where patient_id = ?";
		
		PreparedStatement getAppointment = connection.prepareStatement(query);
			
		getAppointment.setInt(1, id);
		
		ResultSet rs = getAppointment.executeQuery();
		
		ArrayList<Appointment> results = new ArrayList<Appointment>();
		
		while(rs.next()) {
			int pat_id = rs.getInt(1);
			Date date = rs.getDate(2);
			Time start = rs.getTime(3);
			Time end = rs.getTime(4);
			int den_id = rs.getInt(5);
			int bran_id = rs.getInt(6);
			String status = rs.getString(8);
			String type = rs.getString(7);
			String dentist_name = rs.getString(10);
			String branch_name = rs.getString(9);
			
			results.add(new Appointment(pat_id,date,start,end,den_id,bran_id,status,type,dentist_name,branch_name));
				
		}
		
		connection.close();
		return results;
	}
	
	public Branch[] queryAllBranches() throws SQLException{
		Connection connection = super.getConnection();
		
		String query = "select branchid,name from branch";
		Statement state = connection.createStatement();
		ResultSet rs = state.executeQuery(query);
		
		ArrayList<Branch> branches = new ArrayList<Branch>();
		while(rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			branches.add(new Branch(id,name));
		}
		Branch[] branches2 = new Branch[branches.size()];
		branches2 = branches.toArray(branches2);
		connection.close();
		return branches2;
	}
	
	public Dentist[] getAllDentistFromBranch(int branchid) throws SQLException {
		Connection connection = super.getConnection();
		
		String query = "select * "
				+ "from employee as e join public.user as u on e.employee_id = u.id "
				+ "where e.branchid = ? and e.role = 'D'";
		
		PreparedStatement getDentist = connection.prepareStatement(query);
		
		getDentist.setInt(1, branchid);

		ResultSet rs = getDentist.executeQuery();
		
		ArrayList<Dentist> dentists = new ArrayList<Dentist>();
		while(rs.next()) {
			int id = rs.getInt(1);
			double sal = rs.getDouble(2);
			String r = rs.getString(3);
			String username = rs.getString(4);
			String password = rs.getString(5);
			int bid = rs.getInt(6);
			String name = rs.getString(8);
			
			dentists.add(new Dentist(id,sal,r,username,password,bid,name));
		}
		Dentist[] dentist2 = new Dentist[dentists.size()];
		dentist2 = dentists.toArray(dentist2);
		connection.close();
		return dentist2;
	}
	
	public boolean insertRequest(int patid, Date d, Time st, Time ed, int denid, int branid, String stat, String ty) {
		try {
			Connection connection = super.getConnection();
			
			String query = "insert into appointment values(?,?,?,?,?,?,?,?)";
			PreparedStatement insertRequest = connection.prepareStatement(query);
			
			insertRequest.setInt(1,patid);
			insertRequest.setInt(5,denid);
			insertRequest.setInt(6,branid);
			insertRequest.setDate(2, d);
			insertRequest.setTime(3, st);
			insertRequest.setTime(4, ed);
			insertRequest.setString(7, stat);
			insertRequest.setString(8, ty);
			
			insertRequest.executeUpdate();
			connection.close();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Appointment> searchAppointments(int patid, Object d, int d1, Object ti, int t1, Object denid, Object branid, int bd, Object stat, Object ty) {
		String p = patid+"|"+d+"|"+d1+"|"+ti+"|"+t1+"|"+denid+"|"+branid+"|"+bd+"|"+stat+"|"+ty;
		System.out.println(p);
		try {
			Connection connection = super.getConnection();
			
			String query = "select patient_id, appointment_date,start_time,end_time,dentist_id,branch_id,appointment_type, appointment_status, b.name, u.name "
					+ "from appointment as a "
					+ "join branch as b on (a.branch_id = b.branchid) "
					+ "join public.user as u on (a.dentist_id = u.id) "
					+ "where patient_id = "+patid+" ";
			
			String date="";
			switch(d1) {
				case(-1):
					break;
				case(0):
					date = " and appointment_date = "+"'"+d+"'"+" ";
					break;
				case(1):
					date= " and appointment_date <= "+"'"+d+"'"+" ";
					break;
				case(2):
					date= " and appointment_date >= "+"'"+d+"'"+" ";
					break;
			}
			
			query = query + date;
			
			String time="";
			switch(t1) {
				case(-1):
					break;
				case(0):
					time = " and (start_time <= "+"'"+ti+"'"+"  and end_time >= "+"'"+ti+"'"+") ";
					break;
				case(1):
					time= "and ((start_time <= "+"'"+ti+"'"+"  and end_time >= "+"'"+ti+"'"+") or start_time < "+"'"+ti+"'"+" ) ";
					break;
				case(2):
					time= "and ((start_time <= "+"'"+ti+"'"+"  and end_time >= "+"'"+ti+"'"+") or start_time > "+"'"+ti+"'"+" ) ";
					break;
			}
			
			query = query + time;
			
			String denbran ="";
			switch(bd) {
				case(0):
					break;
				case(1):
					denbran = " and branch_id = "+branid+" ";
					break;
				case(2):
					denbran= " and branch_id = "+branid+" and dentist_id = "+denid+" ";
					break;
			}
			
			query = query + denbran;
			
			String stats="";
			if(stat != null) {
				stats = "and appointment_status = "+"'"+stat+"'"+" ";
			}
			
			query = query + stats;
			
			String tys="";
			if(ty != null) {
				tys = "and appointment_type = "+"'"+ty+"'"+" ";
			}
			
			query = query + tys;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			ArrayList<Appointment> appoints = new ArrayList<Appointment>();
			while(rs.next()) {
				int pat_id = rs.getInt(1);
				Date date1 = rs.getDate(2);
				Time start = rs.getTime(3);
				Time end = rs.getTime(4);
				int den_id = rs.getInt(5);
				int bran_id = rs.getInt(6);
				String status = rs.getString(8);
				String type = rs.getString(7);
				String dentist_name = rs.getString(10);
				String branch_name = rs.getString(9);
				
				appoints.add(new Appointment(pat_id,date1,start,end,den_id,bran_id,status,type,dentist_name,branch_name));
			}
			connection.close();
			return appoints;
			
		}catch(SQLException e){
			e.printStackTrace();
			return new ArrayList<Appointment>();
		}
	}
	

	public static void main(String[] args) throws SQLException {
		PatientDB db = new PatientDB();
		Dentist[] b = db.getAllDentistFromBranch(1);
		System.out.println(b);
		
	}

}
