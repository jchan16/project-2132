package objects;

public class Patient {
	
	private int id;
	private int associated_id;
	private String insurance;
	private String ssn;
	private int street_number;
	private String street_name; 
	private String city;
	private String province; 
	private String postal_code;
	private String username;
	private String password;
	
	public Patient(int i,int ai,String in,String ssn,int sn,String sna,String c,String p,String pc,String u,String pa) {
		this.id  = id;
		this.associated_id = ai;
		this.ssn = ssn;
		this.street_number = sn;
		this.street_name = sna;
		this.city = c;
		this.province = p;
		this.postal_code = pc;
		this.username = u;
		this.password = pa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssociated_id() {
		return associated_id;
	}

	public void setAssociated_id(int associated_id) {
		this.associated_id = associated_id;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getStreet_number() {
		return street_number;
	}

	public void setStreet_number(int street_number) {
		this.street_number = street_number;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
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
	
	
	
}
