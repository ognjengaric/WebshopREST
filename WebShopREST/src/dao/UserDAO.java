package dao;

import java.util.HashMap;

import beans.Seller;
import beans.User;

public class UserDAO {
	
	private HashMap<String, User> users = new HashMap<>();
	
	public UserDAO(){
		User seller1 = new User("seller1", "seller1", "seller", "seller", "555-333", "seller@s.com", "New York", null);
		User seller2 = new User("seller2", "seller2", "seller", "seller", "555-333", "seller@s.com", "New York", null);
		Seller s1 = new Seller();
		Seller s2 = new Seller();
		seller1.setRole(s1);
		seller2.setRole(s2);
		users.put(seller1.getUsername(), seller1);
		users.put(seller2.getUsername(), seller2);
	}
	

	public HashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}
	
	public User find(User u) {
		for(User user: users.values())
		{	
			if(u.getUsername().equals(user.getUsername()))
			{
				if(u.getPassword().equals(user.getPassword()))
				{
					return user;
				}
				else
				{
					return null;
				}
			}
		}		
		return null;
	}
	
	public User findBySessionID(String sessionID) {
		for(User user: users.values()) {
			if(sessionID.equals(user.getAccessToken())){
				return user;
			} 
		}
		
		return null;
	}

	@Override
	public String toString() {
		String retVal = "";
		
		for(User u : this.users.values()) {
			retVal += u.toString();
		}
		
		return retVal;
	}
	
	
	
}
