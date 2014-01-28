package hu.schonherz.y2014.partyappandroid.util.datamodell;

import hu.schonherz.y2014.partyappandroid.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Club {
    public int id;
    public String name;
    public String type;
    public String description;
    public String address;
    public String phonenumber;
    public String email;
    public String date;
    public String highlite_expire;
    public int approved;
    
    public boolean fullDownloaded = false;

    public String ownerName;

    public List<MenuItem> menuItems = new ArrayList<MenuItem>();
    public List<Rating> ratings = new ArrayList<Rating>();
    public List<Event> events = new ArrayList<Event>();
    public List<String> services = new ArrayList<String>();
    public List<GaleryImage> images = new ArrayList<GaleryImage>();

    public Club(int id, String name, String type, String description, String address, String phonenumber, String email,
	    String date, String highlite_expire, int approved) {
	this.id = id;
	this.name = name;
	this.type = type;
	this.description = description;
	this.address = address;
	this.phonenumber = phonenumber;
	this.email = email;
	this.date = date;
	this.highlite_expire = highlite_expire;
	this.approved = approved;
    }

    // probléma megoldó konstruktor ( date kivéve, nem tudjuk hogy mi az )
    public Club(int id, String name, String type, String description, String address, String phonenumber, String email,
	    String highlight_expire, int approved) {
	this.id = id;
	this.name = name;
	this.type = type;
	this.description = description;
	this.address = address;
	this.phonenumber = phonenumber;
	this.email = email;
	this.date = ""; // !!!!!!
	this.highlite_expire = highlight_expire;
	this.approved = approved; // !!!!!!
    }

    public Club(int id, String name, String type, String description, String address, String phonenumber, String email,
	    String highlite_expire, int approved, List<String> services) {
	super();
	this.id = id;
	this.name = name;
	this.type = type;
	this.description = description;
	this.address = address;
	this.phonenumber = phonenumber;
	this.email = email;
	this.highlite_expire = highlite_expire;
	this.approved = approved;
	this.services = services;
	this.date = "";
    }

    public Club(int id, String name, String address) {
	this.id = id;
	this.name = name;
	this.address = address;
    }

    public Club(int id, String name, String address, int approved) {
	this.id = id;
	this.name = name;
	this.address = address;
	this.approved = approved;
    }

    public boolean isNotFullDownloaded() {
	return !fullDownloaded;
    }

    public void downloadEverything() {
	Log.i(this.getClass().getName(), "Minden infó lekérése a klubról ( " + id + " )");

	Club clubWithAllInfo = Session.getInstance().getActualCommunicationInterface().getEverythingFromClub(this.id);
	this.email = clubWithAllInfo.email;
	this.date = clubWithAllInfo.date;
	this.description = clubWithAllInfo.description;

	this.events = Session.getInstance().getActualCommunicationInterface().getEventsOfClub(this.id);
	Log.i(getClass().getName(), "Klub eseményei: " + this.events.size());

	this.menuItems = Session.getInstance().getActualCommunicationInterface().getMenuItemsForClub(this.id);
	this.ratings = Session.getInstance().getActualCommunicationInterface().getRatings(this.id);
	
	this.images.clear();
	ArrayList<Integer> imageIDList = Session.getInstance().getActualCommunicationInterface()
		.selectClubsImagesIds(this.id);
	for (int i = 0; i < imageIDList.size(); i++) {
	    Log.e("galery", "i: " + i);
	    this.images.add(new GaleryImage(imageIDList.get(i), (ImageUtils.StringToBitMap(Session.getInstance()
		    .getActualCommunicationInterface().DownLoadAnImageThumbnail(imageIDList.get(i))))));
	}

	Log.i(this.getClass().getName(), "Klub adatai frissítve ( " + id + " )");
	fullDownloaded=true;
    }

    public Rating isRatingThisUser(int userId) {
	for (Rating rating : ratings) {
	    if (rating.userId == userId)
		return rating;
	}
	return null;
    }

}
