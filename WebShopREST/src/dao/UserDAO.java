package dao;

import java.util.HashMap;

import beans.User;

public class UserDAO {
	
	private HashMap<String, User> users = new HashMap<>();
	
	public UserDAO(){
		users.put("user", new User("user", "user"));
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
					return u;
				}
				else
				{
					return null;
				}
			}
		}		
		return null;
	}
	
	public User findIfRegistered(User u) {
		for(User user: users.values())
		{	
			if(u.getUsername().equals(user.getUsername()))
			{
				return u;
			}
		}		
		return null;
	}
}
