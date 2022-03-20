package objects;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	
	private int patient_id;
	private Date appointment_date;
	private Time start;
	private Time end;
	private int dentist_id;
	private int branch_id;
	private String status;
	private String type;
	private String dentist_name;
	private String branch_name;
	
	public Appointment(int pi, Date ad, Time s, Time e, int di, int bi, String st, String t, String dn, String bn) {
		
		patient_id = pi;
		appointment_date = ad;
		start = s;
		end = e;
		dentist_id = di;
		branch_id = bi;
		status = st; 
		type = t;
		dentist_name =  dn;
		branch_name = bn;
		
	}
	
	public String toString() {
		return "[Date/Time:" + appointment_date+" "+"("+start+" - "+end+")]";
	}
	
	public String description() {
		return "At:"+ branch_name+"\n"+ "With:" + dentist_name + "\n"+ "Type: " + type +"\n"+ "Status: "+ status+"\n" +" Date/Time:" + appointment_date+" "+"("+start+" - "+end+")";
	}
	
	public String getDentist_name() {
		return dentist_name;
	}

	public void setDentist_name(String dentist_name) {
		this.dentist_name = dentist_name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public Date getAppointment_date() {
		return appointment_date;
	}
	public void setAppointment_date(Date appointment_date) {
		this.appointment_date = appointment_date;
	}
	public Time getStart() {
		return start;
	}
	public void setStart(Time start) {
		this.start = start;
	}
	public Time getEnd() {
		return end;
	}
	public void setEnd(Time end) {
		this.end = end;
	}
	public int getDentist_id() {
		return dentist_id;
	}
	public void setDentist_id(int dentist_id) {
		this.dentist_id = dentist_id;
	}
	public int getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	} 
	
	
	
	

}
