package dao;

import java.util.HashMap;

import beans.Ad;
import beans.Seller;
import beans.User;

public class UserDAO {
	
	private HashMap<String, User> users = new HashMap<>();
	
	public UserDAO(){
		User u = this.ConstructUsers();
		users.put(u.getUsername(), u);
	}
	
	private User ConstructUsers() {
		User seller = new User("seller", "seller", "seller", "seller", "555-333", "seller@s.com", "New York", null);
		Seller s = new Seller();
		s.getDeliveredProductAds().add(new Ad("Lupa1", 20,"Ovo je random lupa koja je savrsen predstavnik svoje vrste. Izmedu ostalog, bavi se uvelicavanjem stvari. Mhmmmmmmmm.", 42, 2, "PD94bWwgdmVyc2lvbj0iMS4wIiA/PjxzdmcgaGVpZ2h0PSIzMnB4IiB2ZXJzaW9uPSIxLjEiIHZpZXdCb3g9IjAgMCAzMiAzMiIgd2lkdGg9IjMycHgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6c2tldGNoPSJodHRwOi8vd3d3LmJvaGVtaWFuY29kaW5nLmNvbS9za2V0Y2gvbnMiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj48dGl0bGUvPjxkZXNjLz48ZGVmcy8+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIiBpZD0iUGFnZS0xIiBzdHJva2U9Im5vbmUiIHN0cm9rZS13aWR0aD0iMSI+PGcgZmlsbD0iIzkyOTI5MiIgaWQ9Imljb24tMTExLXNlYXJjaCI+PHBhdGggZD0iTTE5LjQyNzExNjQsMjAuNDI3MTE2NCBDMTguMDM3MjQ5NSwyMS40MTc0ODAzIDE2LjMzNjY1MjIsMjIgMTQuNSwyMiBDOS44MDU1NzkzOSwyMiA2LDE4LjE5NDQyMDYgNiwxMy41IEM2LDguODA1NTc5MzkgOS44MDU1NzkzOSw1IDE0LjUsNSBDMTkuMTk0NDIwNiw1IDIzLDguODA1NTc5MzkgMjMsMTMuNSBDMjMsMTUuODQ3MjEwMyAyMi4wNDg2MDUyLDE3Ljk3MjIxMDMgMjAuNTEwNDA3NywxOS41MTA0MDc3IEwyNi41MDc3NzM2LDI1LjUwNzc3MzYgQzI2Ljc4MjgyOCwyNS43ODI4MjggMjYuNzc2MTQyNCwyNi4yMjM4NTc2IDI2LjUsMjYuNSBDMjYuMjIxOTMyNCwyNi43NzgwNjc2IDI1Ljc3OTYyMjcsMjYuNzc5NjIyNyAyNS41MDc3NzM2LDI2LjUwNzc3MzYgTDE5LjQyNzExNjQsMjAuNDI3MTE2NCBMMTkuNDI3MTE2NCwyMC40MjcxMTY0IFogTTE0LjUsMjEgQzE4LjY0MjEzNTgsMjEgMjIsMTcuNjQyMTM1OCAyMiwxMy41IEMyMiw5LjM1Nzg2NDE3IDE4LjY0MjEzNTgsNiAxNC41LDYgQzEwLjM1Nzg2NDIsNiA3LDkuMzU3ODY0MTcgNywxMy41IEM3LDE3LjY0MjEzNTggMTAuMzU3ODY0MiwyMSAxNC41LDIxIEwxNC41LDIxIFoiIGlkPSJzZWFyY2giLz48L2c+PC9nPjwvc3ZnPg==", "21.02.2018", "19.12.2019", null, "Novi Sad", Ad.Status.DELIVERED));
		s.getDeliveredProductAds().add(new Ad("Lupa2", 20,"Ovo je random lupa koja je savrsen predstavnik svoje vrste. Izmedu ostalog, bavi se uvelicavanjem stvari. Mhmmmmmmmm.", 42, 2, "PD94bWwgdmVyc2lvbj0iMS4wIiA/PjxzdmcgaGVpZ2h0PSIzMnB4IiB2ZXJzaW9uPSIxLjEiIHZpZXdCb3g9IjAgMCAzMiAzMiIgd2lkdGg9IjMycHgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6c2tldGNoPSJodHRwOi8vd3d3LmJvaGVtaWFuY29kaW5nLmNvbS9za2V0Y2gvbnMiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj48dGl0bGUvPjxkZXNjLz48ZGVmcy8+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIiBpZD0iUGFnZS0xIiBzdHJva2U9Im5vbmUiIHN0cm9rZS13aWR0aD0iMSI+PGcgZmlsbD0iIzkyOTI5MiIgaWQ9Imljb24tMTExLXNlYXJjaCI+PHBhdGggZD0iTTE5LjQyNzExNjQsMjAuNDI3MTE2NCBDMTguMDM3MjQ5NSwyMS40MTc0ODAzIDE2LjMzNjY1MjIsMjIgMTQuNSwyMiBDOS44MDU1NzkzOSwyMiA2LDE4LjE5NDQyMDYgNiwxMy41IEM2LDguODA1NTc5MzkgOS44MDU1NzkzOSw1IDE0LjUsNSBDMTkuMTk0NDIwNiw1IDIzLDguODA1NTc5MzkgMjMsMTMuNSBDMjMsMTUuODQ3MjEwMyAyMi4wNDg2MDUyLDE3Ljk3MjIxMDMgMjAuNTEwNDA3NywxOS41MTA0MDc3IEwyNi41MDc3NzM2LDI1LjUwNzc3MzYgQzI2Ljc4MjgyOCwyNS43ODI4MjggMjYuNzc2MTQyNCwyNi4yMjM4NTc2IDI2LjUsMjYuNSBDMjYuMjIxOTMyNCwyNi43NzgwNjc2IDI1Ljc3OTYyMjcsMjYuNzc5NjIyNyAyNS41MDc3NzM2LDI2LjUwNzc3MzYgTDE5LjQyNzExNjQsMjAuNDI3MTE2NCBMMTkuNDI3MTE2NCwyMC40MjcxMTY0IFogTTE0LjUsMjEgQzE4LjY0MjEzNTgsMjEgMjIsMTcuNjQyMTM1OCAyMiwxMy41IEMyMiw5LjM1Nzg2NDE3IDE4LjY0MjEzNTgsNiAxNC41LDYgQzEwLjM1Nzg2NDIsNiA3LDkuMzU3ODY0MTcgNywxMy41IEM3LDE3LjY0MjEzNTggMTAuMzU3ODY0MiwyMSAxNC41LDIxIEwxNC41LDIxIFoiIGlkPSJzZWFyY2giLz48L2c+PC9nPjwvc3ZnPg==", "21.02.2018", "19.12.2019", null, "Novi Sad", Ad.Status.PENDING));
		s.getPublishedAds().add(new Ad("Lupa3", 20,"Ovo je random lupa koja je savrsen predstavnik svoje vrste. Izmedu ostalog, bavi se uvelicavanjem stvari. Mhmmmmmmmm.", 42, 2, "PD94bWwgdmVyc2lvbj0iMS4wIiA/PjxzdmcgaGVpZ2h0PSIzMnB4IiB2ZXJzaW9uPSIxLjEiIHZpZXdCb3g9IjAgMCAzMiAzMiIgd2lkdGg9IjMycHgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6c2tldGNoPSJodHRwOi8vd3d3LmJvaGVtaWFuY29kaW5nLmNvbS9za2V0Y2gvbnMiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj48dGl0bGUvPjxkZXNjLz48ZGVmcy8+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIiBpZD0iUGFnZS0xIiBzdHJva2U9Im5vbmUiIHN0cm9rZS13aWR0aD0iMSI+PGcgZmlsbD0iIzkyOTI5MiIgaWQ9Imljb24tMTExLXNlYXJjaCI+PHBhdGggZD0iTTE5LjQyNzExNjQsMjAuNDI3MTE2NCBDMTguMDM3MjQ5NSwyMS40MTc0ODAzIDE2LjMzNjY1MjIsMjIgMTQuNSwyMiBDOS44MDU1NzkzOSwyMiA2LDE4LjE5NDQyMDYgNiwxMy41IEM2LDguODA1NTc5MzkgOS44MDU1NzkzOSw1IDE0LjUsNSBDMTkuMTk0NDIwNiw1IDIzLDguODA1NTc5MzkgMjMsMTMuNSBDMjMsMTUuODQ3MjEwMyAyMi4wNDg2MDUyLDE3Ljk3MjIxMDMgMjAuNTEwNDA3NywxOS41MTA0MDc3IEwyNi41MDc3NzM2LDI1LjUwNzc3MzYgQzI2Ljc4MjgyOCwyNS43ODI4MjggMjYuNzc2MTQyNCwyNi4yMjM4NTc2IDI2LjUsMjYuNSBDMjYuMjIxOTMyNCwyNi43NzgwNjc2IDI1Ljc3OTYyMjcsMjYuNzc5NjIyNyAyNS41MDc3NzM2LDI2LjUwNzc3MzYgTDE5LjQyNzExNjQsMjAuNDI3MTE2NCBMMTkuNDI3MTE2NCwyMC40MjcxMTY0IFogTTE0LjUsMjEgQzE4LjY0MjEzNTgsMjEgMjIsMTcuNjQyMTM1OCAyMiwxMy41IEMyMiw5LjM1Nzg2NDE3IDE4LjY0MjEzNTgsNiAxNC41LDYgQzEwLjM1Nzg2NDIsNiA3LDkuMzU3ODY0MTcgNywxMy41IEM3LDE3LjY0MjEzNTggMTAuMzU3ODY0MiwyMSAxNC41LDIxIEwxNC41LDIxIFoiIGlkPSJzZWFyY2giLz48L2c+PC9nPjwvc3ZnPg==", "21.02.2018", "19.12.2019", null, "Novi Sad", Ad.Status.PUBLISHED));
		seller.setRole(s);
		
		return seller;
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
