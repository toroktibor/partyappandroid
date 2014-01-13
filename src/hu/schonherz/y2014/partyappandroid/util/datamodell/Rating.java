package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class Rating {
	String userName;
	float value;
	String comment;
	int approved;
	
	public Rating(String userName, float value, String comment, int approved) {
		this.userName = userName;
		this.value = value;
		this.comment = comment;
		this.approved = approved;
	}
}
