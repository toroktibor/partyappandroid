package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class Rating {
    public int userId;
    public String userName;
    public float value;
    public String comment;
    public int approved;

    public Rating(int userId, String userName, float value, String comment, int approved) {
        this.userId = userId;
        this.userName = userName;
        this.value = value;
        this.comment = comment;
        this.approved = approved;
    }
}
