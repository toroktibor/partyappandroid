package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

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

    public List<MenuItem> menuItems;
    public List<Rating> ratings;
    public List<Event> events;
    public List<String> services;

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

    public Club(int id, String name, String address) {
	this.id = id;
	this.name = name;
	this.address = address;
    }
    
    public boolean isNotFullDownloaded(){
    	return (this.email == null) && (this.date == null) && (this.description == null) && (this.events == null);
    }

}
