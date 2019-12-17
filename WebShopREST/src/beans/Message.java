package beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import beans.Ad.Status;
import helpers.UUIDGenerator;

public class Message {
	
	private String id;
	private String sender;
	private String recipient;
	private String title;
	private String content;
	private String dateAndTime;
	
	private boolean isDeletedSender;
	private boolean isDeletedRecipient;
	private boolean isRead;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	public Message() {
		this.id = UUIDGenerator.getUUID();
		this.isRead = false;
		this.isDeletedSender = false;
		this.isDeletedRecipient = false;
		this.dateAndTime = LocalDateTime.now().format(formatter);
	}
	
	public Message(String sender, String recipient, String title, String content) {
		this.id = UUIDGenerator.getUUID();
		this.isDeletedRecipient = false;
		this.dateAndTime = LocalDateTime.now().format(formatter);
		
		this.sender = sender;
		this.recipient = recipient;
		this.title = title;
		this.content = content;
	}
	
	public static String adAction(String adName, Status adStatus, String username, String role, String action) {
		return "Ad named '" + adName + "' with status '" + adStatus + "' has been " + action +"!\nIt was " + action + " by '" + username +"' with role '" + role + "'.";
	};
	
	public static String reviewAction(String title, String readOrWrite, String adName, String author) {
		return "Review with title '" + title + "' in ad '" + adName +"' has been " + readOrWrite + " by " + author + "'.";
	};
	
	public static String successfullyDelivered(String adName, String username) {
		return "Product '" + adName + "' has been successfully delivered to '" + username + "'.";
	} 
	
	public static String descriptionNotAccurate(String adName, String username) {
		return "Warning!\nYour ad '" + adName + "' does not contain correct description according to user '" + username + "'."; 
	}
	
	public static String dealNotFullfilled(String username, String adName) {
		return "Warning!\nDeal has not been fullfilled with user '" + username + "' regarding ad '" + adName + "'.";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean getIsDeletedSender() {
		return isDeletedSender;
	}

	public void setIsDeletedSender(boolean isDeletedSender) {
		this.isDeletedSender = isDeletedSender;
	}

	public boolean getIsDeletedRecipient() {
		return isDeletedRecipient;
	}

	public void setIsDeletedRecipient(boolean isDeletedRecipient) {
		this.isDeletedRecipient = isDeletedRecipient;
	}	
	
}
