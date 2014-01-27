package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class AdminRating extends Rating {
	
	public int clubId;
	public String clubName;
	
	public AdminRating(int userId, String userName, float value,
			String comment, int approved, int clubId, String clubName) {
		super(userId, userName, value, comment, approved);
		this.clubId = clubId;
		this.clubName = clubName;
		// TODO Auto-generated constructor stub
	}

}
