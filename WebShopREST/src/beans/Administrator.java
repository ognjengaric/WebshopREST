package beans;

public class Administrator{
	
	private String name;
	
	public Administrator() {
		this.setName("Administrator");
	}	
	
	
	@Override
	public String toString() {
		return "\nRole: Administrator";
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
}
