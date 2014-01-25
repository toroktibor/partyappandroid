package hu.schonherz.y2014.partyappandroid.util.datamodell;

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

	public String ownerName;

	public List<MenuItem> menuItems = new ArrayList<MenuItem>();
	public List<Rating> ratings = new ArrayList<Rating>();
	public List<Event> events = new ArrayList<Event>();
	public List<String> services = new ArrayList<String>();

	public Club(int id, String name, String type, String description,
			String address, String phonenumber, String email, String date,
			String highlite_expire, int approved) {
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
	public Club(int id, String name, String type, String description,
			String address, String phonenumber, String email,
			String highlight_expire, int approved) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.address = address;
		this.phonenumber = phonenumber;
		this.email = email;
		this.date = null; // !!!!!!
		this.highlite_expire = highlight_expire;
		this.approved = approved; // !!!!!!
	}

	public Club(int id, String name, String type, String description,
			String address, String phonenumber, String email,
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
		return (this.email == null) && (this.date == null)
				&& (this.description == null) && (this.events == null);
	}
	
	public void downloadEverything(){
	    Log.i(this.getClass().getName(),"Minden infó lekérése a klubról ( "+id+" )");
	    
	    Club clubWithAllInfo = Session.getInstance().getActualCommunicationInterface().getEverythingFromClub(this.id);
	    this.email=clubWithAllInfo.email;
	    this.date=clubWithAllInfo.date;
	    this.description=clubWithAllInfo.description;
	    this.events=clubWithAllInfo.events;
	    
	    Log.i(this.getClass().getName(),"Klub adatai frissítve ( "+id+" )");
	}
	
	public Rating isRatingThisUser(int userId){
		for(Rating rating : ratings){
			if(rating.userId==userId)
				return rating;
		}
		return null;
	}
	
}
