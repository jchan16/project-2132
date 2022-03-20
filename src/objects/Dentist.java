package objects;

public class Dentist {
	
	private int employee_id;
	private double salary;
	private String role;
	private String username;
	private String password;
	private int branchid;
	private String name;
	
	public Dentist(int eid, double s, String r, String u, String p, int bid, String n) {
		employee_id = eid;
		salary = s;
		role = r;
		username = u;
		password = p;
		branchid = bid;
		name = n;
	}
	
	public String toString() {
		return name;
	}
	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBranchid() {
		return branchid;
	}

	public void setBranchid(int branchid) {
		this.branchid = branchid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
