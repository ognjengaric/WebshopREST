package dao;

import java.util.HashMap;

import beans.User;

public class UserDAO {
	
	private HashMap<String, User> users = new HashMap<>();
	
	public UserDAO(){
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
