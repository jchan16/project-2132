package objects;

public class Branch {
	private int branchid;
	private String name;
	
	public Branch(int id, String n) {
		branchid = id;
		name = n;
	}
	
	public String toString() {
		return name;
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
