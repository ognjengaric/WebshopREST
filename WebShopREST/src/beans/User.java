package beans;

public class User {
	
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected Object role;
	protected String telephoneNumber;
	protected String email;
	protected String city;
	protected String registrationDate;
	
	//Pri svakom registrovanju 
	public User() {
		this.role = new Buyer();
		this.registrationDate = java.time.LocalDate.now().toString();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Object getRole() {
		return role;
	}
	public void setRole(Object role) {
		this.role = role;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String toString() {
		String retVal= "";
		
		retVal += "\nUsername: " + this.username;
		retVal += "\nPassword: " + this.password;
		retVal += "\nFirstname: " + this.firstName;
		retVal += "\nLastname: " + this.lastName;
		retVal += this.role.toString();
		retVal += "\nTelephone number : " + this.telephoneNumber;
		retVal += "\nEmail: " + this.email;
		retVal += "\nCity: " + this.city;
		retVal += "\nRegistration date: " + this.registrationDate;
		
		return retVal;
	}
	
	

}
