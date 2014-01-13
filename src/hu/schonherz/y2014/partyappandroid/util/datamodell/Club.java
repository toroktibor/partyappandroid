package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class Club {
	int id;
	String name;
	String type;
	String description;
	String address;
	String phonenumber;
	String email;
	String date;
	String highlite_expire;
	int approved;
	
	String ownerName;
	
	List<MenuItem> menuItems;
	List<Rating> ratings;
	List<Event> events;
	List<String> services;
	
	
	public Club(int id, String name, String type, String description, String address, String phonenumber, String email, String date, String highlite_expire, int approved) {
		this.id=id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.address = address;
		this.phonenumber = phonenumber;
		this.email = email;
		this.date = date;
		this.highlite_expire=highlite_expire;
		this.approved = approved;
	}
	
	public Club(int id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}
}
