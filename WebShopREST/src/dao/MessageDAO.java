package dao;

import java.util.HashMap;

import beans.Message;

public class MessageDAO {
	private HashMap<String, Message> messages = new HashMap<String, Message>();
	
	public  MessageDAO() {
		
	}

	public HashMap<String, Message> getMessages() {
		return messages;
	}

	public void setMessages(HashMap<String, Message> messages) {
		this.messages = messages;
	}
	
	
	
}
