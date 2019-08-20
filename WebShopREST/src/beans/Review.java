package beans;

public class Review {
	
	//oglas - ime ili objekat
	//kupac koji je napisao recenziju - ime ili objekat
	private String title;
	private String content;
	//slika
	private boolean isDescriptionAccurate;
	private boolean isDealFulfilled;
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
	public boolean isDescriptionAccurate() {
		return isDescriptionAccurate;
	}
	public void setDescriptionAccurate(boolean isDescriptionAccurate) {
		this.isDescriptionAccurate = isDescriptionAccurate;
	}
	public boolean isDealFulfilled() {
		return isDealFulfilled;
	}
	public void setDealFulfilled(boolean isDealFulfilled) {
		this.isDealFulfilled = isDealFulfilled;
	}
	
	
}
