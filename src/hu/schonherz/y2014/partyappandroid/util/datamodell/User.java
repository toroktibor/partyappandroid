package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class User {
    private int id;
    private String nickname;
    private String password;
    private String email;
    /**
     * 0 = Férfi 1 = Nő
     */
    private int sex;
    private String birthday;
    /**
     * A 0 = vendég vagy tulaj 1 = admin
     */
    private int type;

    public List<Club> favoriteClubs;
    public List<Club> usersClubs;

    public static Club searchInLocalList(int club_id, List<Club> clubs) {
        for (int i = 0; i < clubs.size(); i++) {
            if (clubs.get(i).id == club_id) {
                return clubs.get(i);
            }
        }
        return null;
    }

    public User(int id, String nickname, String password, String email, int sex, String birthday, int type) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.type = type;
    }

    public boolean isThisUserOwnerOfClub(int clubID) {
        for (Club actualClub : usersClubs) {
            if (actualClub.id == clubID)
                return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getType() {
        return type;
    }

    public void modifyUserData(String email, String birthday, int sex) throws Exception {
        Session.getInstance().getActualCommunicationInterface().modifyUserData(id, email, birthday, sex);
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
    }

    public void modifyPassword(String newPassword) throws Exception {
        Session.getInstance().getActualCommunicationInterface().modifyPassword(id, newPassword);
        this.password = newPassword;
    }

    public String toString() {
        return String.format("id: %d nickname: %s password: %s email: %s " + "sex: %d birhtday: %s type: %d", id,
                nickname, password, email, sex, birthday, type);
    }

    public boolean isMine(int clubId) {
        for (Club actualClub : this.usersClubs) {
            if (actualClub.id == clubId)
                return true;
        }
        return false;
    }

    public boolean isLike(int clubId) {
        for (Club actualClub : this.favoriteClubs) {
            if (actualClub.id == clubId)
                return true;
        }
        return false;
    }
}
