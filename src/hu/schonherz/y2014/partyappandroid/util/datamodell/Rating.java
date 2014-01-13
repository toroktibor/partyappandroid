package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class Rating {
	public String userName;
	public float value;
	public String comment;
	public int approved;
	
	public Rating(String userName, float value, String comment, int approved) {
		this.userName = userName;
		this.value = value;
		this.comment = comment;
		this.approved = approved;
	}
}
