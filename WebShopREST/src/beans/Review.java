package beans;

import helpers.UUIDGenerator;

public class Review {
	
	private String id;
	private String adName;
	private String author;
	private String title;
	private String content;
	private String image;
	private boolean isDescriptionAccurate;
	private boolean isDealFulfilled;
	
	private boolean isActive;
	
	public Review() {
		this.isActive = true;
		this.setId(UUIDGenerator.getUUID());
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
	public boolean getIsDescriptionAccurate() {
		return isDescriptionAccurate;
	}
	public void setIsDescriptionAccurate(boolean isDescriptionAccurate) {
		this.isDescriptionAccurate = isDescriptionAccurate;
	}
	public boolean getIsDealFulfilled() {
		return isDealFulfilled;
	}
	public void setIsDealFulfilled(boolean isDealFulfilled) {
		this.isDealFulfilled = isDealFulfilled;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String reviewAuthor) {
		this.author = reviewAuthor;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
