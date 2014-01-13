package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class Event {
	int id;
	String name;
	String description;
	String start_date;
	String music_type;
	
	public Event(int id, String name, String description, String start_date, String music_type) {
		this.id = id;
		this.name = name;
		this. description = description;
		this.start_date = start_date;
		this.music_type = music_type;
	}
}
