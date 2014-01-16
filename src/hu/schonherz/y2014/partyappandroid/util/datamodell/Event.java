package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class Event {
    public int id;
    public String name;
    public String description;
    public String start_date;
    public String music_type;
    public int approved;

    public Event(int id, String name, String description, String start_date, String music_type, int approved) {
	this.id = id;
	this.name = name;
	this.description = description;
	this.start_date = start_date;
	this.music_type = music_type;
	this.approved = approved;
    }
}
