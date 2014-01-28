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
    
    public Club(int id, String name, String address, String highlite_expire) {
    	this.id = id;
    	this.name = name;
    	this.address = address;
    	this.highlite_expire = highlite_expire;
    }
    
    public Club(int id, String name, String address, int approved, String highlite_expire) {
    	this.id = id;
    	this.name = name;
    	this.address = address;
    	this.approved = approved;
    	this.highlite_expire = highlite_expire;
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
	this.phonenumber = clubWithAllInfo.phonenumber;
	this.approved = clubWithAllInfo.approved;
	this.type = clubWithAllInfo.type;
	this.highlite_expire = clubWithAllInfo.highlite_expire;

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
    if(this.ratings == null || this.ratings.size()==0)
    	return null;
	for (Rating rating : ratings) {
	    if (rating.userId == userId)
		return rating;
	}
	return null;
    }
    
    
    public static int getClubTypePosition(String category){
    	if(category.equals("Vegyes")){
    		return 0;
    	}
    	if(category.equals("Kocsma")){
    		return 1;
    	}
    	if(category.equals("Pub")){
    		return 2;
    	}
    	if(category.equals("Club")){
    		return 3;
    	}
    	if(category.equals("Étterem")){
    		return 4;
    	}
    	if(category.equals("Sport központ")){
    		return 5;
    	}
    	if(category.equals("Disco")){
    		return 6;
    	}
    	
    	return 0;
    }
    
    public float getAvarageRate(){
    	float out = (float) 0.0;
    	if(this.ratings==null || this.ratings.size()==0){
    		return out;
    	}
    	for(Rating rating : this.ratings){
    		out += rating.value;
    	}
    	return out/this.ratings.size();
    }
}
