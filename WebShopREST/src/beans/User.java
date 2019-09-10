package beans;


public class User {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Object role;
	private String telephoneNumber;
	private String email;
	private String city;
	private String registrationDate;
	
	
	private String accessToken;
	
	//Pri svakom registrovanju 
	public User() {
		this.role = new Buyer();
		this.registrationDate = java.time.LocalDate.now().toString();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.role = new Administrator();
		this.registrationDate = java.time.LocalDate.now().toString();
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
	

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		String retVal= "";
		
		retVal += "\nUsername: " + this.username;
		retVal += "\nPassword: " + this.password;
		retVal += "\nFirstname: " + this.firstName;
		retVal += "\nLastname: " + this.lastName;
		retVal += "\nRole: " + this.role.toString();
		retVal += "\nTelephone number : " + this.telephoneNumber;
		retVal += "\nEmail: " + this.email;
		retVal += "\nCity: " + this.city;
		retVal += "\nRegistration date: " + this.registrationDate;
		retVal += "\nAcess token: " + this.accessToken;
		
		return retVal;
	}
	
	

}
