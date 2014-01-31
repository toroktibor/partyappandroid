package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class OwnerRequest {

    Club club;
    User user;

    public OwnerRequest(Club club, User user) {
        this.club = club;
        this.user = user;
    }

    public Club getClub() {
        return club;
    }

    public User getUser() {
        return user;
    }
}
